package ru.pkg.repository.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.repository.AbstractVotingRepositoryTest;

import static ru.pkg.Profiles.JDBC;
import static ru.pkg.Profiles.POSTGRESQL;

@ActiveProfiles({POSTGRESQL, JDBC})
public class JdbcVotingRepositoryTest extends AbstractVotingRepositoryTest {
}