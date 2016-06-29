package ru.pkg.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.pkg.service.AbstractRestaurantServiceTest;

import static ru.pkg.Profiles.JPA;

@ActiveProfiles(JPA)
public class RestaurantServiceJpaTest extends AbstractRestaurantServiceTest {
}
