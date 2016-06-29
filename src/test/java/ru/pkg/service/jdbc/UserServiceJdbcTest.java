package ru.pkg.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractUserServiceTest;

import static ru.pkg.Profiles.*;

@ActiveProfiles(JDBC)
public class UserServiceJdbcTest extends AbstractUserServiceTest {
}
