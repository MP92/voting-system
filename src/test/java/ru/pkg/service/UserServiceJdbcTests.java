package ru.pkg.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.pkg.Profiles.*;

@ActiveProfiles({POSTGRESQL, JDBC})
public class UserServiceJdbcTests extends AbstractUserServiceTests {
}
