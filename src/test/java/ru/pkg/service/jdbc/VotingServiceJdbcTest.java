package ru.pkg.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractVotingServiceTest;

import static ru.pkg.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class VotingServiceJdbcTest extends AbstractVotingServiceTest {
}