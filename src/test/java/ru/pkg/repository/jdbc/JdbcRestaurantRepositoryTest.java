package ru.pkg.repository.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.repository.AbstractRestaurantRepositoryTest;

import static ru.pkg.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcRestaurantRepositoryTest extends AbstractRestaurantRepositoryTest {
}