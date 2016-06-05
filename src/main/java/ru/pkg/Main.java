package ru.pkg;

import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;

import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(
                        new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);

        appCtx.getEnvironment().setActiveProfiles(Profiles.POSTGRESQL, Profiles.JDBC);
        appCtx.refresh();

        System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

        RestaurantRepository restaurantRepository = appCtx.getBean("jdbcRestaurantRepository", RestaurantRepository.class);
        System.out.println(restaurantRepository.getClass().getName());
        List<Restaurant> list = restaurantRepository.findAllWithMenu();
        System.out.println(list);
    }
}
