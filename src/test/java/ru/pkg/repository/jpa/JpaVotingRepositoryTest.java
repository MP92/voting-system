package ru.pkg.repository.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.repository.AbstractVotingRepositoryTest;

import static ru.pkg.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaVotingRepositoryTest extends AbstractVotingRepositoryTest {
}
