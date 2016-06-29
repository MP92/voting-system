package ru.pkg.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractUserServiceTest;

import static ru.pkg.Profiles.JPA;

@ActiveProfiles(JPA)
public class UserServiceJpaTest extends AbstractUserServiceTest {
}
