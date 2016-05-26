package ru.pkg.service;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.pkg.model.User;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static ru.pkg.UserTestData.*;

@ContextConfiguration("classpath:spring/mock.xml")
public class UserServiceMockTests extends AbstractUserServiceTests {

    @Test
    public void testGet() {
        when(repository.findById(0)).thenReturn(ADMIN);
        User user = service.findById(0);
        verify(repository).findById(0);
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
        User newUser = new User(0, NEW_USER);
        when(repository.save(newUser)).thenReturn(true);
        service.update(newUser);
        verify(repository).save(newUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUpdateNotFound() {
        User newUser = new User(10000, NEW_USER);
        when(repository.save(newUser)).thenReturn(false);
        service.update(newUser);
    }

    @Test
    public void testDelete() {
        when(repository.delete(0)).thenReturn(true);
        service.delete(0);
        verify(repository).delete(0);
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
