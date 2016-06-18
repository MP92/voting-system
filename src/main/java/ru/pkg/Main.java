package ru.pkg;

import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pkg.service.DishService;
import ru.pkg.service.RestaurantService;
import ru.pkg.service.UserService;

import static org.slf4j.LoggerFactory.getLogger;

public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) {

        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml"}, false);
        appCtx.getEnvironment().setActiveProfiles(Profiles.JDBC, Profiles.POSTGRESQL);
        appCtx.refresh();

        UserService userService = appCtx.getBean(UserService.class);
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
        LOG.debug(restaurantService.findAllWithMenu().toString());
        LOG.debug("==========\n");
        LOG.debug(restaurantService.findAll().toString());
        LOG.debug("==========\n");
        LOG.debug(restaurantService.findAllWithMenu().toString());

        DishService dishService = appCtx.getBean(DishService.class);
        LOG.debug(dishService.findInMenu(100).toString());
        LOG.debug("==========\n");
        LOG.debug(dishService.findInAllMenus().toString());
        LOG.debug("==========\n");
        LOG.debug(dishService.findInMenu(100).toString());
        LOG.debug("==========\n");
        LOG.debug(dishService.findInAllMenus().toString());

    }
}
