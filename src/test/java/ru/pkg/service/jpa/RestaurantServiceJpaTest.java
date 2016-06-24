package ru.pkg.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractRestaurantServiceTest;

import static ru.pkg.Profiles.JPA;
import static ru.pkg.Profiles.POSTGRESQL;

@ActiveProfiles({POSTGRESQL, JPA})
public class RestaurantServiceJpaTest extends AbstractRestaurantServiceTest {
}
