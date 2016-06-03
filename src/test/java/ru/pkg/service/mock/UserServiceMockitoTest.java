package ru.pkg.service.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.service.UserService;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static ru.pkg.UserTestData.*;

public class UserServiceMockitoTest extends AbstractServiceMockitoTests {

    @Autowired
    protected UserService service;

    @Autowired
    protected UserRepository repository;

    @Test
    public void testGet() {
        when(repository.findById(ADMIN_ID)).thenReturn(ADMIN);
        MATCHER.assertEquals(ADMIN, service.findById(ADMIN_ID));
        verify(repository).findById(ADMIN_ID);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetNotFound() {
        service.findById(NOT_FOUND_INDEX);
    }

    @Test
    public void testAdd() {
        User toCreateUser = new TestUser(NEW_USER);
        when(repository.save(toCreateUser)).thenReturn(toCreateUser);
        User created = service.add(toCreateUser);
        verify(repository).save(toCreateUser);
        MATCHER.assertEquals(toCreateUser, created);
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
        service.update(toUpdateUser);
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
        service.delete(NOT_FOUND_INDEX);
    }

    @Test
    public void testFindAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(ADMIN, USER));
        MATCHER.assertCollectionsEquals(ALL_USERS, service.findAll());
    }
}
