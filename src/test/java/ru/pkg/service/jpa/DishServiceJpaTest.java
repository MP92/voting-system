package ru.pkg.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractDishServiceTest;

import static ru.pkg.Profiles.JPA;
import static ru.pkg.Profiles.POSTGRESQL;

@ActiveProfiles({POSTGRESQL, JPA})
public class DishServiceJpaTest extends AbstractDishServiceTest {
}