package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.UserVote;
import ru.pkg.repository.VotingRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcVotingRepository extends JdbcDaoSupport implements VotingRepository {

    private static final RowMapper<UserVote> USER_VOTE_MAPPER = BeanPropertyRowMapper.newInstance(UserVote.class);

    @Autowired
    public JdbcVotingRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Transactional
    @Override
    public UserVote save(UserVote userVote) {
        if (getJdbcTemplate().update("UPDATE votes SET restaurant_id=?, last_voted=now() WHERE user_id=?", userVote.getRestaurantId(), userVote.getUserId()) == 0) {
            return null;
        }
        return userVote;
    }

    @Override
    public UserVote findById(int userId) {
        return DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM votes WHERE user_id=?", USER_VOTE_MAPPER, userId));
    }

    @Override
    public List<UserVote> findAll() {
        return getJdbcTemplate().query("SELECT * FROM votes WHERE restaurant_id IS NOT NULL AND last_voted >= now()::date ORDER BY user_id", USER_VOTE_MAPPER);
    }

    @Transactional
    @Override
    public boolean delete(int userId) {
        return getJdbcTemplate().update("UPDATE votes SET restaurant_id=NULL, last_voted=NULL WHERE user_id=?", userId) > 0;
    }

    @Transactional
    @Override
    public void reset() {
        getJdbcTemplate().execute("UPDATE votes SET restaurant_id=NULL, last_voted=NULL");
    }
}
