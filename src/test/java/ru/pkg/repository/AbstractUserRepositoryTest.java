package ru.pkg.repository;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import ru.pkg.model.User;

import static ru.pkg.testdata.UserTestData.*;

import java.util.Arrays;

public abstract class AbstractUserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testAdd() {
        User toCreateUser = new TestUser(NEW_USER).asUser();
        User created = repository.save(toCreateUser);
        toCreateUser.setId(created.getId());
        MATCHER.assertEquals(toCreateUser, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, toCreateUser, USER_1, USER_2), repository.findAll());
    }

    @Test
    public void testFindById() {
        User user = repository.findById(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testFindByIdNotFound() {
        Assert.assertNull(repository.findById(NOT_FOUND_INDEX));
    }

    @Test
    public void testFindAll() {
        MATCHER.assertCollectionsEquals(ALL_USERS, repository.findAll());
    }

    @Test
    public void testUpdate() {
        User toUpdateUser = new TestUser(USER_1_ID, NEW_USER).asUser();
        User updated = repository.save(toUpdateUser);
        Assert.assertTrue(updated.getId() == USER_1_ID);
        MATCHER.assertEquals(toUpdateUser, updated);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, updated, USER_2), repository.findAll());
    }

    @Test
    public void testUpdateNotFound() {
        User toUpdateUser = new TestUser(NOT_FOUND_INDEX, NEW_USER).asUser();
        Assert.assertNull(repository.save(toUpdateUser));
        MATCHER.assertCollectionsEquals(ALL_USERS, repository.findAll());
    }

    @Test
    public void testDelete() {
        Assert.assertTrue(repository.delete(ADMIN_ID));

        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), repository.findAll());
    }

    @Test
    public void testDeleteNotFound() {
        Assert.assertFalse(repository.delete(NOT_FOUND_INDEX));
        MATCHER.assertCollectionsEquals(ALL_USERS, repository.findAll());
    }
}
