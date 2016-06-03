package ru.pkg.service;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.User;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Arrays;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.pkg.UserTestData.*;
import static ru.pkg.UserTestData.NEW_USER;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;
    
    @Test
    public void testGet() {
        User user = service.findById(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetNotFound() {
        User user = service.findById(1000);
    }

    @Test
    public void testAdd() {
        User toCreateUser = new TestUser(NEW_USER);
        service.add(toCreateUser);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, toCreateUser, USER), service.findAll());
    }
    
    @Test
    public void testUpdate() {
        TestUser toUpdateUser = new TestUser(ADMIN_ID, NEW_USER);
        service.update(toUpdateUser.asUser());
        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateUser, USER), service.findAll());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testUpdateNotFound() {
        service.update(new TestUser(10000, NEW_USER));
    }
    
    @Test
    public void testDelete() {
        service.delete(ADMIN_ID);
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
