package ru.pkg.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractDishServiceTest;

import static ru.pkg.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class DishServiceJdbcTest extends AbstractDishServiceTest {
}