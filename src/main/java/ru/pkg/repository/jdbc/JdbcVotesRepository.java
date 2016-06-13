package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Votes;
import ru.pkg.repository.VotesRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional(readOnly = false)
public class JdbcVotesRepository extends JdbcDaoSupport implements VotesRepository {

    private static final RowMapper<Votes> VOTES_MAPPER = BeanPropertyRowMapper.newInstance(Votes.class);

    @Autowired
    public JdbcVotesRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Transactional
    @Override
    public void up(int restaurantId) {
        getJdbcTemplate().update("UPDATE votes SET count = count + 1 WHERE restaurant_id=?", restaurantId);
    }

    @Transactional
    @Override
    public void down(int restaurantId) {
        getJdbcTemplate().update("UPDATE votes SET count = GREATEST(count - 1, 0) WHERE restaurant_id=?", restaurantId);
    }

    @Override
    public Votes findById(int restaurantId) {
        String query = "SELECT v.*, r.name as restaurant_name FROM votes as v INNER JOIN restaurants as r ON (v.restaurant_id = r.id) WHERE restaurant_id=?";
        return DataAccessUtils.singleResult(getJdbcTemplate().query(query, VOTES_MAPPER, restaurantId));
    }

    @Override
    public Integer findCount(int restaurantId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT count FROM votes WHERE restaurant_id=?", Integer.class, restaurantId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Votes> findAll() {
        return getJdbcTemplate().query("SELECT v.*, r.name as restaurant_name FROM votes as v INNER JOIN restaurants as r ON (v.restaurant_id = r.id) ORDER BY restaurant_id", VOTES_MAPPER);
    }

    @Transactional
    @Override
    public void reset() {
        getJdbcTemplate().update("UPDATE votes SET count = 0");
    }
}
