package ru.pkg.repository.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.repository.AbstractUserRepositoryTest;

import static ru.pkg.Profiles.*;

@ActiveProfiles({POSTGRESQL, JDBC})
public class JdbcUserRepositoryTest extends AbstractUserRepositoryTest {
}
