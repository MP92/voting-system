package ru.pkg.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Arrays;
import java.util.Collection;

import static ru.pkg.UserTestData.*;
import static ru.pkg.UserTestData.NEW_USER;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractUserServiceTests {

    @Autowired
    protected UserService service;

    @Autowired
    protected UserRepository repository;
    
    @Before
    public void before() {
        repository.clear();
        repository.save(ADMIN);
        repository.save(USER);
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
