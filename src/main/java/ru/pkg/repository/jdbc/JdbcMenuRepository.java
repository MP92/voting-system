package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Menu;
import ru.pkg.repository.MenuRepository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcMenuRepository extends JdbcDaoSupport implements MenuRepository {
    
    private static final RowMapper<Menu> MENU_MAPPER = (rs, rowNum) -> {
        int restaurantId = rs.getInt("restaurant_id");
        List<Integer> dishIDs = Arrays.stream((Integer[])rs.getArray("dish_ids").getArray()).sorted().collect(Collectors.toList());
        return new Menu(restaurantId, dishIDs);
    };

    @Autowired
    public JdbcMenuRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Transactional
    @Override
    public void save(int dishId, int restaurantId) {
        getJdbcTemplate().update("INSERT INTO menus(restaurant_id, dish_id) VALUES (?, ?)", restaurantId, dishId);
    }

    @Transactional
    @Override
    public void save(Menu menu) {
        if (menu.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("INSERT INTO menus(restaurant_id, dish_id) VALUES (%1$d, ?)");
        for (int i = 1; i < menu.getDishIDs().size(); i++) {
            sb.append(", (%1$d, ?)");
        }
        String query = String.format(sb.toString(), menu.getRestaurantId());

        getJdbcTemplate().update(query, menu.getDishIDs().toArray());
/*        getJdbcTemplate().batchUpdate("INSERT INTO menus(restaurant_id, dish_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, restaurantId);
                        ps.setInt(2, dishIDs.et(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return dishIDs.size();
                    }
                }
        );*/
    }

    @Override
    public List<Menu> findAll() {
        return getJdbcTemplate().query("SELECT restaurant_id, array_agg(dish_id) as dish_ids from menus GROUP BY restaurant_id ORDER BY restaurant_id", MENU_MAPPER);
    }

    @Override
    public Menu findById(int restaurantId) {
        if (isRestaurantExists(restaurantId)) {
            List<Integer> dishIDs = getJdbcTemplate().queryForList("SELECT dish_id FROM menus WHERE restaurant_id=? ORDER BY dish_id", Integer.class, restaurantId);
            return new Menu(restaurantId, dishIDs);
        }
        return null;
    }

    @Transactional
    @Override
    public boolean delete(int dishId, int restaurantId) {
        return getJdbcTemplate().update("DELETE FROM menus WHERE restaurant_id=? AND dish_id=?", restaurantId, dishId) > 0;
    }

    @Transactional
    @Override
    public void deleteAll(int restaurantId) {
        getJdbcTemplate().execute("DELETE FROM menus WHERE restaurant_id=" + restaurantId);
    }

    private boolean isRestaurantExists(int restaurantId) {
        return getJdbcTemplate().queryForObject("SELECT exists(SELECT 1 FROM restaurants WHERE id=?)", Boolean.class, restaurantId);
    }
}
