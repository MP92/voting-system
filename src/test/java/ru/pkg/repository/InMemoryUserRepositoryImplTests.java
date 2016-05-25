package ru.pkg.repository;


import org.junit.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pkg.model.User;

import java.util.Arrays;
import java.util.Collection;

import static ru.pkg.UserTestData.*;

public class InMemoryUserRepositoryImplTests {

    private static ConfigurableApplicationContext appCtx;

    private static UserRepository repository;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");

        repository = appCtx.getBean(UserRepository.class);
    }

    @Before
    public void before() {
        repository.clear();
        repository.save(ADMIN);
        repository.save(USER);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Test
    public void testFindAll() {
        MATCHER.assertCollectionsEquals(ALL_USERS, repository.findAll());
    }

    @Test
    public void testAdd() {
        Assert.assertTrue(repository.save(NEW_USER));
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER, NEW_USER), repository.findAll());
    }

    @Test
    public void testDelete() {
        Assert.assertTrue(repository.delete(0));

        Collection<User> all = repository.findAll();
        Assert.assertEquals(1, all.size());
        MATCHER.assertEquals(USER, all.iterator().next());
    }

    @Test
    public void testDeleteNotFound() {
        Assert.assertFalse(repository.delete(1000));
    }

    @Test
    public void testUpdate() {
        User newUser = new User(0, NEW_USER);
        Assert.assertTrue(repository.save(newUser));
        MATCHER.assertCollectionsEquals(Arrays.asList(newUser, USER), repository.findAll());
    }

    @Test
    public void testUpdateNotFound() {
        User newUser = new User(10000, NEW_USER);
        Assert.assertFalse(repository.save(newUser));
    }

    @Test
    public void testGet() {
        User user = repository.findById(0);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testGetNotFound() {
        User user = repository.findById(1000);
        Assert.assertNull(user);
    }
}
