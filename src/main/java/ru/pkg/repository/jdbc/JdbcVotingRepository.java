package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import ru.pkg.model.UserVote;
import ru.pkg.repository.VotingRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static ru.pkg.repository.jdbc.JdbcRepositoryUtils.*;

@Repository
public class JdbcVotingRepository extends NamedParameterJdbcDaoSupport implements VotingRepository {

    private static final RowMapper<UserVote> USER_VOTE_MAPPER = (rs, rowNum) -> new UserVote(rs.getInt("user_id"), getInt(rs.getString("restaurant_id")), getDateTime(rs.getString("last_voted")));

    @Autowired
    public JdbcVotingRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public UserVote save(int userId, int restaurantId) {
        LocalDateTime ldt;
        if (getJdbcTemplate().update("UPDATE votes SET restaurant_id=?, last_voted=? WHERE user_id=?", restaurantId, Timestamp.valueOf(ldt = LocalDateTime.now()), userId) == 0) {
            return null;
        }
        return new UserVote(userId, restaurantId, ldt);
    }

    @Override
    public UserVote findById(int userId) {
        return DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM votes WHERE user_id=?", USER_VOTE_MAPPER, userId));
    }

    @Override
    public List<UserVote> findAll() {
        return getJdbcTemplate().query("SELECT * FROM votes WHERE restaurant_id IS NOT NULL AND last_voted >= now()::date ORDER BY user_id", USER_VOTE_MAPPER);
    }

    @Override
    public boolean delete(int userId) {
        return getJdbcTemplate().update("UPDATE votes SET restaurant_id=NULL, last_voted=NULL WHERE user_id=?", userId) > 0;
    }

    @Override
    public void reset() {
        getJdbcTemplate().execute("UPDATE votes SET restaurant_id=NULL, last_voted=NULL");
    }
}
