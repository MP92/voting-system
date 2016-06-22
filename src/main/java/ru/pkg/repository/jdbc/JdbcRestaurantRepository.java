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
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.utils.DishUtil;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcRestaurantRepository extends NamedParameterJdbcDaoSupport implements RestaurantRepository {

    private static final RowMapper<Restaurant> RESTAURANT_MAPPER = BeanPropertyRowMapper.newInstance(Restaurant.class);

    private static final RowMapper<Dish> DISH_MAPPER = BeanPropertyRowMapper.newInstance(Dish.class);
    private static final RowMapper<JdbcDish> JDBC_DISH_MAPPER = BeanPropertyRowMapper.newInstance(JdbcDish.class);

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
            insertVoteRecord(restaurant);
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
        Restaurant restaurant = DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT r.*, vs.votes FROM restaurants as r LEFT JOIN voting_statistics as vs ON (r.id = vs.restaurant_id) WHERE id=?", RESTAURANT_MAPPER, id));
        loadMenu(restaurant);
        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = getJdbcTemplate().query("SELECT r.*, vs.votes FROM restaurants as r LEFT JOIN voting_statistics as vs ON (r.id = vs.restaurant_id) ORDER BY name", RESTAURANT_MAPPER);
        loadMenus(restaurants);
        return restaurants;
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return getJdbcTemplate().update("DELETE FROM restaurants WHERE id=?", id) > 0;
    }


    private void insertVoteRecord(Restaurant r) {
        getJdbcTemplate().update("INSERT INTO voting_statistics (restaurant_id, votes) VALUES (?, 0)", r.getId());
    }

    private void loadMenu(Restaurant r) {
        if (r != null) {
            List<Dish> menu = getJdbcTemplate().query("SELECT * FROM dishes WHERE restaurant_id=? AND in_menu=TRUE ORDER BY id", DISH_MAPPER, r.getId());
            r.setMenu(menu);
        }
    }

    private void loadMenus(List<Restaurant> restaurants) {
        Map<Integer, List<Dish>> menus = getJdbcTemplate().query("SELECT * FROM dishes WHERE in_menu=TRUE ORDER BY id", JDBC_DISH_MAPPER).stream().collect(Collectors.groupingBy(JdbcDish::getRestaurantId, Collectors.mapping(DishUtil::asDish, Collectors.toList())));
        restaurants.forEach(r -> r.setMenu(menus.getOrDefault(r.getId(), Collections.emptyList())));
    }
}
