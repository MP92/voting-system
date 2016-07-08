package ru.pkg.service.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.service.UserService;
import ru.pkg.utils.exception.UserNotFoundException;


import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static ru.pkg.testdata.UserTestData.*;
import static ru.pkg.utils.EntityUtils.prepareToSave;

public class UserServiceMockitoTest extends AbstractServiceMockitoTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() throws Exception {
        reset(repository);
    }

    @Test
    public void testAdd() {
        User toCreateUser = new TestUser(null, NEW_USER);
        when(repository.save(toCreateUser)).thenAnswer(invocation -> {
            toCreateUser.setId(CREATED_USER_ID);
            return toCreateUser;
        });
        User created = service.add(toCreateUser);
        verify(repository).save(toCreateUser);
        Assert.assertTrue(toCreateUser.getId() == CREATED_USER_ID);
        MATCHER.assertEquals(toCreateUser, created);
    }

    @Test
    public void testFindById() {
        when(repository.findById(ADMIN_ID)).thenReturn(prepareToSave(new TestUser(ADMIN)));
        MATCHER.assertEquals(ADMIN, service.findById(ADMIN_ID));
        verify(repository).findById(ADMIN_ID);
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindByIdNotFound() {
        when(repository.findById(NOT_FOUND_INDEX)).thenReturn(null);
        try {
            service.findById(NOT_FOUND_INDEX);
        } catch (UserNotFoundException e) {
            verify(repository).findById(NOT_FOUND_INDEX);
            throw e;
        }
    }

    @Test
    public void testFindAll() {
        List<User> users = ALL_USERS.stream().map(u -> prepareToSave(new TestUser(u))).collect(Collectors.toList());
        when(repository.findAll()).thenReturn(users);
        MATCHER.assertCollectionsEquals(ALL_USERS, service.findAll());
    }

    @Test
    public void testUpdate() {
        User toUpdateUser = new TestUser(ADMIN_ID, NEW_USER);
        when(repository.save(toUpdateUser)).thenReturn(toUpdateUser);
        service.update(toUpdateUser);
        verify(repository).save(toUpdateUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUpdateNotFound() {
        User toUpdateUser = new TestUser(NOT_FOUND_INDEX, NEW_USER);
        when(repository.save(toUpdateUser)).thenReturn(null);
        try {
            service.update(toUpdateUser);
        } catch (UserNotFoundException e) {
            verify(repository).save(toUpdateUser);
            throw e;
        }
    }

    @Test
    public void testDelete() {
        when(repository.delete(ADMIN_ID)).thenReturn(true);
        service.delete(ADMIN_ID);
        verify(repository).delete(ADMIN_ID);
    }

    @Test(expected = UserNotFoundException.class)
    public void testDeleteNotFound() {
        when(repository.delete(NOT_FOUND_INDEX)).thenReturn(false);
        try {
            service.delete(NOT_FOUND_INDEX);
        } catch (UserNotFoundException e) {
            verify(repository).delete(NOT_FOUND_INDEX);
            throw e;
        }
    }
}
