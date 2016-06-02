package ru.pkg.repository;

import org.springframework.test.context.ActiveProfiles;

import static ru.pkg.Profiles.*;

@ActiveProfiles({POSTGRESQL, JDBC})
public class JdbcUserRepositoryTests extends AbstractUserRepositoryTests {
}
