package ru.pkg.repository.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.repository.AbstractUserRepositoryTest;

import static ru.pkg.Profiles.JPA;
import static ru.pkg.Profiles.POSTGRESQL;

@ActiveProfiles({POSTGRESQL, JPA})
public class JpaUserRepositoryTest extends AbstractUserRepositoryTest {
}
