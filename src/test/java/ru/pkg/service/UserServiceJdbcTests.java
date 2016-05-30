package ru.pkg.service;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static ru.pkg.Profiles.*;

@ContextConfiguration("classpath:spring/spring-db.xml")
@ActiveProfiles({POSTGRESQL, JDBC})
@Sql("classpath:db/populateDB.sql")
public class UserServiceJdbcTests extends AbstractUserServiceTests {
}
