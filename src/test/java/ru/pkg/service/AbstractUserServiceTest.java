package ru.pkg.service;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.User;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Arrays;

import static ru.pkg.testdata.UserTestData.*;
import static ru.pkg.testdata.UserTestData.NEW_USER;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void testAdd() {
        User toCreateUser = new TestUser(NEW_USER);
        User created = service.add(toCreateUser);
        toCreateUser.setId(created.getId());
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, toCreateUser, USER_1, USER_2), service.findAll());
    }
    
    @Test
    public void testFindById() {
        User user = service.findById(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindByIdNotFound() {
        service.findById(1000);
    }

    @Test
    public void testFindAll() {
        MATCHER.assertCollectionsEquals(ALL_USERS, service.findAll());
    }
    
    @Test
    public void testUpdate() {
        TestUser toUpdateUser = new TestUser(ADMIN_ID, NEW_USER);
        service.update(toUpdateUser.asUser());
        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateUser, USER_1, USER_2), service.findAll());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testUpdateNotFound() {
        service.update(new TestUser(10000, NEW_USER));
    }
    
    @Test
    public void testDelete() {
        service.delete(ADMIN_ID);
        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), service.findAll());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testDeleteNotFound() {
        service.delete(1000);
    }
}
