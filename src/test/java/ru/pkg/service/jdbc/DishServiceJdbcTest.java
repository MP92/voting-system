package ru.pkg.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractDishServiceTest;

import static ru.pkg.Profiles.JDBC;
import static ru.pkg.Profiles.POSTGRESQL;

@ActiveProfiles({POSTGRESQL, JDBC})
public class DishServiceJdbcTest extends AbstractDishServiceTest {
}