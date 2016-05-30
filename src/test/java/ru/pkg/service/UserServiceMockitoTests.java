package ru.pkg.service;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.pkg.model.User;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static ru.pkg.UserTestData.*;

@ContextConfiguration("classpath:spring/MockUserRepository-context.xml")
public class UserServiceMockitoTests extends AbstractUserServiceTests {

    @Test
    public void testGet() {
        when(repository.findById(ADMIN_ID)).thenReturn(ADMIN);
        User user = service.findById(ADMIN_ID);
        verify(repository).findById(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetNotFound() {
        User user = service.findById(1000);
    }

    @Test
    public void testAdd() {
        service.add(NEW_USER);
        verify(repository).save(NEW_USER);
    }

    @Test
    public void testUpdate() {
        User newUser = new TestUser(ADMIN_ID, NEW_USER);
        when(repository.save(newUser)).thenReturn(newUser);
        service.update(newUser);
        verify(repository).save(newUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUpdateNotFound() {
        User newUser = new TestUser(10000, NEW_USER);
        when(repository.save(newUser)).thenReturn(null);
        service.update(newUser);
    }

    @Test
    public void testDelete() {
        when(repository.delete(ADMIN_ID)).thenReturn(true);
        service.delete(ADMIN_ID);
        verify(repository).delete(ADMIN_ID);
    }

    @Test(expected = UserNotFoundException.class)
    public void testDeleteNotFound() {
        when(repository.delete(1000)).thenReturn(false);
        service.delete(1000);
    }

    @Test
    public void testFindAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(ADMIN, USER));
        MATCHER.assertCollectionsEquals(ALL_USERS, service.findAll());
    }
}
