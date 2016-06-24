package ru.pkg.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractVotingServiceTest;

import static ru.pkg.Profiles.JPA;
import static ru.pkg.Profiles.POSTGRESQL;

@ActiveProfiles({POSTGRESQL, JPA})
public class VotingServiceJpaTest extends AbstractVotingServiceTest {
}