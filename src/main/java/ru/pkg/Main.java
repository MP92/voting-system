package ru.pkg;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pkg.model.*;
import ru.pkg.repository.DishRepository;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.repository.UserRepository;
import ru.pkg.repository.VotingRepository;
import ru.pkg.service.RestaurantService;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.pkg.web.json.JacksonObjectMapper.getMapper;

public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) throws JsonProcessingException {

        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml"}, false);
        appCtx.getEnvironment().setActiveProfiles(Profiles.JDBC, Profiles.H2);
        appCtx.refresh();

        UserRepository userRepository = appCtx.getBean(UserRepository.class);
        RestaurantRepository restaurantRepository = appCtx.getBean(RestaurantRepository.class);
        DishRepository dishRepository = appCtx.getBean(DishRepository.class);
        VotingRepository votingRepository = appCtx.getBean(VotingRepository.class);

        RestaurantService restaurantService = appCtx.getBean(RestaurantService.class);
        List<Restaurant> all = restaurantService.findAll();
        System.out.println(all);
        System.out.println(getMapper().writeValueAsString(all));
    }
}
