package ru.pkg.repository.jdbc;


import org.springframework.jdbc.core.RowMapper;
import ru.pkg.model.Dish;
import ru.pkg.model.DishCategory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Helpful class that provides useful jdbc features
 */
public class JdbcRepositoryUtils {

    public static final RowMapper<Dish> DISH_MAPPER = (rs, rowNum) -> new Dish(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
            rs.getInt("weight"), DishCategory.valueOf(rs.getString("category")), rs.getDouble("price"), rs.getBoolean("in_menu"));

    public static Integer getInt(String value) {
        return value != null ? Integer.parseInt(value) : null;
    }

    public static LocalDateTime getDateTime(String value) {
        return value != null ? Timestamp.valueOf(value).toLocalDateTime() : null;
    }
}
