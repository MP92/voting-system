package ru.pkg.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractRestaurantServiceTest;

import static ru.pkg.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class RestaurantServiceJdbcTest extends AbstractRestaurantServiceTest {
}
