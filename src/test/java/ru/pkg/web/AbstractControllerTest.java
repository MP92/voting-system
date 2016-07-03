package ru.pkg.web;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.pkg.AbstractTest;
import ru.pkg.service.*;

import javax.annotation.PostConstruct;

import static ru.pkg.Profiles.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-web.xml",
})
@WebAppConfiguration
@Sql(value = "classpath:db/populateDB-test.sql")
@ActiveProfiles({ACTIVE_DB, ACTIVE_DAO_IMPL})
public abstract class AbstractControllerTest extends AbstractTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected UserService userService;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected DishService dishService;

    @Autowired
    protected VotingService votingService;

    @PostConstruct
    void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @After
    public void tearDown() throws Exception {
        cacheManager.getCache("users").clear();
        cacheManager.getCache("restaurants").clear();
        cacheManager.getCache("dishes").clear();
    }
}
