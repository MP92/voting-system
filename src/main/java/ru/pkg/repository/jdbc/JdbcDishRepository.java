package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.DishRepository;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.utils.exception.DishNotFoundException;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcDishRepository extends NamedParameterJdbcDaoSupport implements DishRepository {

    private static final RowMapper<Dish> DISH_MAPPER = BeanPropertyRowMapper.newInstance(Dish.class);

    private SimpleJdbcInsert inserter;

    @Autowired
    public JdbcDishRepository(DataSource dataSource) {
        setDataSource(dataSource);
        inserter = new SimpleJdbcInsert(dataSource)
                    .withTableName("dishes")
                    .usingGeneratedKeyColumns("id");
    }

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public Dish save(Dish dish, int restaurantId) throws DishNotFoundException, DataIntegrityViolationException {
        if (dish.isNew() && restaurantRepository.findById(restaurantId) == null) {
            return null;
        }

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", dish.getId())
                .addValue("restaurant_id", restaurantId)
                .addValue("name", dish.getName())
                .addValue("description", dish.getDescription())
                .addValue("weight", dish.getWeight())
                .addValue("category", dish.getCategory().toString())
                .addValue("price", dish.getPrice())
                .addValue("in_menu", dish.isInMenu());

        if (dish.isNew()) {
            Number key = inserter.executeAndReturnKey(parameters);
            dish.setId(key.intValue());
        } else {
            String query = "UPDATE dishes SET name=:name, description=:description, weight=:weight, category=:category, price=:price, in_menu=:in_menu WHERE id=:id AND restaurant_id=:restaurant_id";
            if (getNamedParameterJdbcTemplate().update(query, parameters) == 0) {
                return null;
            }
        }
        return dish;
    }

    @Override
    public Dish findById(int id, int restaurantId) throws DishNotFoundException {
        Dish dish = DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM dishes WHERE id=? AND restaurant_id=?", DISH_MAPPER, id, restaurantId));
        loadRestaurant(dish, restaurantId);
        return dish;
    }

    @Override
    public List<Dish> findAll(int restaurantId) {
        List<Dish> dishes = getJdbcTemplate().query("SELECT * FROM dishes WHERE restaurant_id=? ORDER BY id", DISH_MAPPER, restaurantId);
        loadRestaurants(dishes, restaurantId);
        return dishes;
    }

    @Override
    public boolean delete(int id, int restaurantId) throws DishNotFoundException {
        return getJdbcTemplate().update("DELETE FROM dishes WHERE id=? AND restaurant_id=?", id, restaurantId) > 0;
    }

    private void loadRestaurant(Dish dish, int restaurantId) {
        if (dish != null) {
            dish.setRestaurant(restaurantRepository.findById(restaurantId));
        }
    }

    private void loadRestaurants(List<Dish> dishes, int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        dishes.forEach(dish -> dish.setRestaurant(restaurant));
    }
}
