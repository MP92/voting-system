package ru.pkg.repository.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.repository.AbstractMenuRepositoryTest;

import static ru.pkg.Profiles.JDBC;
import static ru.pkg.Profiles.POSTGRESQL;

@ActiveProfiles({POSTGRESQL, JDBC})
public class JdbcMenuRepositoryTest extends AbstractMenuRepositoryTest {
}