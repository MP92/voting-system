package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;

import javax.sql.DataSource;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcRestaurantRepository extends NamedParameterJdbcDaoSupport implements RestaurantRepository {

    private static final RowMapper<Restaurant> RESTAURANT_MAPPER = new BeanPropertyRowMapper<>(Restaurant.class);

    private SimpleJdbcInsert inserter;

    @Autowired
    public JdbcRestaurantRepository(DataSource dataSource) {
        setDataSource(dataSource);
        inserter = new SimpleJdbcInsert(dataSource)
                .withTableName("restaurants")
                .usingGeneratedKeyColumns("id");
    }

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(restaurant);

        if (restaurant.isNew()) {
            Number key = inserter.executeAndReturnKey(parameterSource);
            restaurant.setId(key.intValue());
            createVoteRecord(restaurant);
        } else {
            String query = "UPDATE restaurants SET name=:name, description=:description, address=:address, phone_number=:phoneNumber WHERE id=:id";
            if (getNamedParameterJdbcTemplate().update(query, parameterSource) == 0) {
                return null;
            }
        }
        return restaurant;
    }

    @Override
    public Restaurant findById(int id) {
        return DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT r.*, vs.votes FROM restaurants as r LEFT JOIN voting_statistics as vs ON (r.id = vs.restaurant_id) WHERE id=?", RESTAURANT_MAPPER, id));
    }

    @Override
    public List<Restaurant> findAll() {
        return getJdbcTemplate().query("SELECT r.*, vs.votes FROM restaurants as r LEFT JOIN voting_statistics as vs ON (r.id = vs.restaurant_id) ORDER BY name", RESTAURANT_MAPPER);
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return getJdbcTemplate().update("DELETE FROM restaurants WHERE id=?", id) > 0;
    }

    @Transactional
    private void createVoteRecord(Restaurant r) {
        getJdbcTemplate().update("INSERT INTO voting_statistics (restaurant_id, votes) VALUES (?, 0)", r.getId());
    }
}
