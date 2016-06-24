package ru.pkg;

import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pkg.model.Restaurant;
import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.model.UserVote;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.repository.UserRepository;
import ru.pkg.repository.VotingRepository;
import ru.pkg.service.RestaurantService;
import ru.pkg.service.UserService;

import java.time.LocalDateTime;
import java.util.EnumSet;

import static org.slf4j.LoggerFactory.getLogger;

public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) {

        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml"}, false);
        appCtx.getEnvironment().setActiveProfiles(Profiles.JPA, Profiles.POSTGRESQL);
        appCtx.refresh();

       UserRepository userRepository = appCtx.getBean(UserRepository.class);

        User toUpdateUser = new User(2, "test", "test", "password", Role.ROLE_USER);
        User updated = userRepository.save(toUpdateUser);
        User hmm = userRepository.findById(2);
        userRepository.findById(2);
        System.out.println(hmm);
/*
        RestaurantRepository restaurantRepository = appCtx.getBean(RestaurantRepository.class);
        Restaurant restaurant = restaurantRepository.findById(100);*/




/*        UserService userService = appCtx.getBean(UserService.class);
        LOG.debug(userService.findAll().toString());
        LOG.debug("==========\n");
        LOG.debug(userService.findAll().toString());
        userService.delete(1);
        LOG.debug("==========\n");
        LOG.debug(userService.findAll().toString());

        LOG.debug("==========\n\n");

        RestaurantService restaurantService = appCtx.getBean(RestaurantService.class);
        LOG.debug(restaurantService.findAll().toString());
        LOG.debug("==========\n");
        LOG.debug(restaurantService.findAll().toString());*/
    }
}
