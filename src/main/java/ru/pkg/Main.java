package ru.pkg;

import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pkg.model.*;
import ru.pkg.repository.DishRepository;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.repository.UserRepository;
import ru.pkg.repository.VotingRepository;

import static org.slf4j.LoggerFactory.getLogger;

public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) {

        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml"}, false);
        appCtx.getEnvironment().setActiveProfiles(Profiles.JPA, Profiles.POSTGRESQL);
        appCtx.refresh();

        UserRepository userRepository = appCtx.getBean(UserRepository.class);
        RestaurantRepository restaurantRepository = appCtx.getBean(RestaurantRepository.class);
        DishRepository dishRepository = appCtx.getBean(DishRepository.class);
        VotingRepository votingRepository = appCtx.getBean(VotingRepository.class);
    }
}
