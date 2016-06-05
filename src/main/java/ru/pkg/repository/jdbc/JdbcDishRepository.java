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
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Dish;
import ru.pkg.repository.DishRepository;
import ru.pkg.utils.exception.DishNotFoundException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
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

    @Override
    @Transactional
    public Dish save(Dish dish) throws DishNotFoundException, DataIntegrityViolationException {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", dish.getId())
                .addValue("restaurant_id", dish.getRestaurantId())
                .addValue("name", dish.getName())
                .addValue("description", dish.getDescription())
                .addValue("weight", dish.getWeight())
                .addValue("category", dish.getCategory().toString())
                .addValue("price", dish.getPrice());

        if (dish.isNew()) {
            Number key = inserter.executeAndReturnKey(parameters);
            dish.setId(key.intValue());
        } else {
            String query = "UPDATE dishes SET name=:name, description=:description, weight=:weight, category=:category, price=:price WHERE id=:id AND restaurant_id=:restaurant_id";
            if (getNamedParameterJdbcTemplate().update(query, parameters) == 0) {
                return null;
            }
        }
        return dish;
    }

    @Override
    public Dish findById(int id, int restaurantId) throws DishNotFoundException {
        List<Dish> list = getJdbcTemplate().query("SELECT * FROM dishes WHERE id=? AND restaurant_id=?", DISH_MAPPER, id, restaurantId);
        return DataAccessUtils.singleResult(list);
    }

    @Override
    public List<Dish> findMenu(int restaurantId) {
        return getJdbcTemplate().query("SELECT * FROM dishes WHERE id in (SELECT dish_id FROM menus WHERE restaurant_id=?) ORDER BY id", DISH_MAPPER, restaurantId);
    }

    @Override
    public Map<Integer, List<Dish>> findAllMenus() {
        String menusQuery = "SELECT * from dishes INNER JOIN menus ON dishes.id=menus.dish_id ORDER BY id";
        return getJdbcTemplate().query(menusQuery, DISH_MAPPER).stream().collect(Collectors.groupingBy(Dish::getRestaurantId));
    }

    @Override
    public List<Dish> findAll(int restaurantId) {
        return getJdbcTemplate().query("SELECT * FROM dishes WHERE restaurant_id=? ORDER BY id", DISH_MAPPER, restaurantId);
    }

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId) throws DishNotFoundException {
        return getJdbcTemplate().update("DELETE FROM dishes WHERE id=? AND restaurant_id=?", id, restaurantId) > 0;
    }
}
