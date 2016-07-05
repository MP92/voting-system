package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.pkg.model.Dish;
import ru.pkg.model.DishCategory;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

import static ru.pkg.repository.jdbc.JdbcRepositoryUtils.DISH_MAPPER;

@Repository
public class JdbcRestaurantRepository extends NamedParameterJdbcDaoSupport implements RestaurantRepository {

    private static final RowMapper<Restaurant> RESTAURANT_MAPPER = BeanPropertyRowMapper.newInstance(Restaurant.class);

    private static final RowMapper<JdbcDish> JDBC_DISH_MAPPER = (rs, rowNum) -> new JdbcDish(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
            rs.getInt("weight"), DishCategory.valueOf(rs.getString("category")), rs.getDouble("price"), rs.getBoolean("in_menu"), rs.getInt("restaurant_id"));

    private SimpleJdbcInsert inserter;

    @Autowired
    public JdbcRestaurantRepository(DataSource dataSource) {
        setDataSource(dataSource);
        inserter = new SimpleJdbcInsert(dataSource)
                .withTableName("restaurants")
                .usingGeneratedKeyColumns("id");
    }

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
        }
        return restaurant;
    }

    @Override
    public Restaurant findById(int id) {
        Restaurant restaurant = DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM restaurants WHERE id=?", RESTAURANT_MAPPER, id));
        loadMenu(restaurant);
        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        return getJdbcTemplate().query("SELECT * FROM restaurants ORDER BY name", RESTAURANT_MAPPER);
    }

    @Override
    public List<Restaurant> findAllWithMenu() {
        List<Restaurant> restaurants = findAll();
        loadMenus(restaurants);
        return restaurants;
    }

    @Override
    public boolean delete(int id) {
        return getJdbcTemplate().update("DELETE FROM restaurants WHERE id=?", id) > 0;
    }


    private void loadMenu(Restaurant r) {
        if (r != null) {
            List<Dish> menu = getJdbcTemplate().query("SELECT * FROM dishes WHERE restaurant_id=? AND in_menu=TRUE ORDER BY id", DISH_MAPPER, r.getId());
            r.setMenu(menu);
        }
    }

    private void loadMenus(List<Restaurant> restaurants) {
        Map<Integer, List<Dish>> menus = getJdbcTemplate().query("SELECT * FROM dishes WHERE in_menu=TRUE ORDER BY id", JDBC_DISH_MAPPER).stream().collect(Collectors.groupingBy(JdbcDish::getRestaurantId, Collectors.mapping(JdbcDish::asDish, Collectors.toList())));
        restaurants.forEach(r -> r.setMenu(menus.getOrDefault(r.getId(), Collections.emptyList())));
    }

    /**
     * Subclass of Dish that carries temporary restaurant id property which is only relevant for a JDBC implementation
     * of RestaurantRepository.
     */
    public static class JdbcDish extends Dish {

        private int restaurantId;

        public JdbcDish(Integer id, String name, String description, int weight, DishCategory category, double price, boolean inMenu, int restaurantId) {
            super(id, name, description, weight, category, price, inMenu);
            this.restaurantId = restaurantId;
        }

        public int getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(int restaurantId) {
            this.restaurantId = restaurantId;
        }

        public static Dish asDish(JdbcDish jdbcDish) {
            return new Dish(jdbcDish.getId(), jdbcDish.getName(), jdbcDish.getDescription(), jdbcDish.getWeight(), jdbcDish.getCategory(), jdbcDish.getPrice(), jdbcDish.isInMenu());
        }
    }
}
