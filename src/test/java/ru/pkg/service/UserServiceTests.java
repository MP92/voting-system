package ru.pkg.service;

import org.junit.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Arrays;
import java.util.Collection;

import static ru.pkg.UserTestData.*;
import static ru.pkg.UserTestData.NEW_USER;

public class UserServiceTests {

    private static ConfigurableApplicationContext appCtx;

    private static UserService service;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");

        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");

        service = appCtx.getBean(UserService.class);
    }
    
    @Before
    public void before() {
        UserRepository repository = appCtx.getBean(UserRepository.class);
        repository.clear();
        repository.save(ADMIN);
        repository.save(USER);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }
    
    @Test
    public void testGet() {
        User user = service.findById(0);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetNotFound() {
        User user = service.findById(1000);
    }

    @Test
    public void testAdd() {
        service.add(NEW_USER);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER, NEW_USER), service.findAll());
    }
    
    @Test
    public void testUpdate() {
        User newUser = new User(0, NEW_USER);
        service.update(newUser);
        MATCHER.assertCollectionsEquals(Arrays.asList(newUser, USER), service.findAll());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testUpdateNotFound() {
        User newUser = new User(10000, NEW_USER);
        service.update(newUser);
    }
    
    @Test
    public void testDelete() {
        service.delete(0);

        Collection<User> all = service.findAll();
        Assert.assertEquals(1, all.size());
        MATCHER.assertEquals(USER, all.iterator().next());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testDeleteNotFound() {
        service.delete(1000);
    }
    
    @Test
    public void testFindAll() {
        MATCHER.assertCollectionsEquals(ALL_USERS, service.findAll());
    }
}
