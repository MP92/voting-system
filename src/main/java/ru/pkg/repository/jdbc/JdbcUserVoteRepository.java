package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.UserVote;
import ru.pkg.repository.UserVoteRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcUserVoteRepository extends JdbcDaoSupport implements UserVoteRepository {

    private static final RowMapper<UserVote> USER_VOTE_MAPPER = BeanPropertyRowMapper.newInstance(UserVote.class);

    @Autowired
    public JdbcUserVoteRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Transactional
    @Override
    public UserVote save(UserVote userVote) {
        if (isUserVotedToday(userVote)) {
            throw new DataIntegrityViolationException("User with id=" + userVote.getUserId() + " already voted today");
        }
        if (getJdbcTemplate().update("UPDATE votes SET restaurant_id=?, date_time=now() WHERE user_id=?", userVote.getRestaurantId(), userVote.getUserId()) == 0) {
            getJdbcTemplate().update("INSERT INTO votes (user_id, restaurant_id) VALUES (?, ?)", userVote.getUserId(), userVote.getRestaurantId());
        }
        getJdbcTemplate().update("UPDATE voting_statistics SET votes = votes + 1 WHERE restaurant_id=?", userVote.getRestaurantId());
        return userVote;
    }

    @Override
    public UserVote findById(int userId) {
        return DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM votes WHERE user_id=?", USER_VOTE_MAPPER, userId));
    }

    @Override
    public List<UserVote> findAll() {
        return getJdbcTemplate().query("SELECT * FROM votes ORDER BY user_id", USER_VOTE_MAPPER);
    }

    @Transactional
    @Override
    public boolean delete(int userId) {
        UserVote userVote = DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM votes WHERE user_id=?", USER_VOTE_MAPPER, userId));
        if (userVote == null || getJdbcTemplate().update("DELETE FROM votes WHERE user_id=?", userId) == 0) {
            return false;
        }
        return getJdbcTemplate().update("UPDATE voting_statistics SET votes = GREATEST(votes - 1, 0) WHERE restaurant_id=?", userVote.getRestaurantId()) > 0;
    }

    @Transactional
    @Override
    public void reset() {
        getJdbcTemplate().update("DELETE FROM votes");
        getJdbcTemplate().update("UPDATE voting_statistics SET votes = 0");
    }

    private boolean isUserVotedToday(UserVote userVote) {
        return getJdbcTemplate().queryForObject("SELECT exists(SELECT 1 FROM votes WHERE user_id=? AND date_time >= now()::date)", Boolean.class, userVote.getUserId());
    }
}
