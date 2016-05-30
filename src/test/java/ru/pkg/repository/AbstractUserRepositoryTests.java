package ru.pkg.repository;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.pkg.model.User;

import java.util.Arrays;
import java.util.Collection;

import static ru.pkg.UserTestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractUserRepositoryTests {

    @Autowired
    protected UserRepository repository;

    @Test
    public void testFindAll() {
        MATCHER.assertCollectionsEquals(ALL_USERS, repository.findAll());
    }

    @Test
    public void testAdd() {
        User newUser = new TestUser(NEW_USER);
        User created = repository.save(newUser);
        MATCHER.assertEquals(newUser, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, newUser, USER), repository.findAll());
    }

    @Test
    public void testDelete() {
        Assert.assertTrue(repository.delete(ADMIN_ID));

        Collection<User> all = repository.findAll();
        Assert.assertEquals(1, all.size());
        MATCHER.assertEquals(USER, all.iterator().next());
    }

    @Test
    public void testDeleteNotFound() {
        Assert.assertFalse(repository.delete(1000));
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER), repository.findAll());
    }

    @Test
    public void testUpdate() {
        User toUpdateUser = new TestUser(USER_ID, NEW_USER);
        User updated = repository.save(toUpdateUser);
        MATCHER.assertEquals(toUpdateUser, updated);
        MATCHER.assertEquals(toUpdateUser, repository.findById(toUpdateUser.getId()));
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, updated), repository.findAll());
    }

    @Test
    public void testUpdateNotFound() {
        User newUser = new TestUser(10000, NEW_USER);
        Assert.assertNull(repository.save(newUser));
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER), repository.findAll());
    }

    @Test
    public void testGet() {
        User user = repository.findById(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testGetNotFound() {
        User user = repository.findById(1000);
        Assert.assertNull(user);
    }
}
