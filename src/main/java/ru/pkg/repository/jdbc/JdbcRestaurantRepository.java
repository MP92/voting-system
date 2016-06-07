package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.DishRepository;
import ru.pkg.repository.RestaurantRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcRestaurantRepository extends NamedParameterJdbcDaoSupport implements RestaurantRepository {

    private static final RowMapper<Restaurant> RESTAURANT_MAPPER = new BeanPropertyRowMapper<>(Restaurant.class);

    private SimpleJdbcInsert inserter;

    @Autowired
    private DishRepository dishRepository;

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
        } else {
            String query = "UPDATE restaurants SET name=:name, description=:description, address=:address, phone_number=:phoneNumber WHERE id=:id";
            if (getNamedParameterJdbcTemplate().update(query, parameterSource) == 0) {
                return null;
            }
            deleteMenu(restaurant);
            insertMenu(restaurant);
        }
        return restaurant;
    }

    @Override
    public Restaurant findById(int id) {
        Restaurant restaurant = DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM restaurants WHERE id=?", RESTAURANT_MAPPER, id));
        fetchMenu(restaurant);
        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        return getJdbcTemplate().query("SELECT * FROM restaurants ORDER BY name", RESTAURANT_MAPPER);
    }

    @Override
    public List<Restaurant> findAllWithMenu() {
        List<Restaurant> restaurants = findAll();
        Map<Integer, List<Dish>> menus = dishRepository.findAllMenus();
        restaurants.forEach(r -> r.setMenu(menus.getOrDefault(r.getId(), Collections.emptyList())));
        return restaurants;
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return getJdbcTemplate().update("DELETE FROM restaurants WHERE id=?", id) > 0;
    }

    @Override
    public Integer findVotesById(int id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT count FROM votes WHERE restaurant_id=?", Integer.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<Integer, Integer> findAllVotes() {
        return getJdbcTemplate().query("SELECT restaurant_id, count FROM votes", rs -> {
            Map<Integer, Integer> resultMap = new HashMap<>();
            while (rs.next()) {
                resultMap.put(rs.getInt("restaurant_id"), rs.getInt("count"));
            }
            return resultMap;
        });
    }

    @Transactional
    @Override
    public void addVote(int id) {
        Integer votes = findVotesById(id);
        String query = votes != null ? "UPDATE votes SET count = count + 1 WHERE restaurant_id=?"
                                     : "INSERT INTO votes (restaurant_id, count) VALUES (?, 1)";

        getJdbcTemplate().update(query, id);
    }

    @Transactional
    @Override
    public void resetVotes() {
        getJdbcTemplate().update("UPDATE votes SET count = 0");
    }


    private void fetchMenu(Restaurant restaurant) {
        if (restaurant != null) {
            restaurant.setMenu(dishRepository.findMenu(restaurant.getId()));
        }
    }

    private void deleteMenu(Restaurant r) {
        getJdbcTemplate().execute("DELETE FROM menus WHERE restaurant_id=" + r.getId());
    }

    private void insertMenu(Restaurant r) {
        Iterator<Dish> iterator = r.getMenu().iterator();

        getJdbcTemplate().batchUpdate("INSERT INTO menus(restaurant_id, dish_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, r.getId());
                        ps.setInt(2, iterator.next().getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return r.getMenu().size();
                    }
                }
        );
    }

    @Transactional
    @Override
    public void addDishToMenu(int id, int dishId) {
        getJdbcTemplate().update("INSERT INTO menus(restaurant_id, dish_id) VALUES (?, ?)", id, dishId);
    }

    @Transactional
    @Override
    public void deleteDishFromMenu(int id, int dishId) {
        getJdbcTemplate().update("DELETE FROM menus WHERE restaurant_id=? AND dish_id=?", id, dishId);
    }
}
