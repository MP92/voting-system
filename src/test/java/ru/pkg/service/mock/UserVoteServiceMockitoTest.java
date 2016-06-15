package ru.pkg.service.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.UserTestData;
import ru.pkg.model.UserVote;
import ru.pkg.repository.UserVoteRepository;
import ru.pkg.service.UserVoteService;
import ru.pkg.utils.exception.VotingException;

import static org.mockito.Mockito.*;
import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.UserTestData.USER_1_ID;
import static ru.pkg.UserVoteTestData.*;
import static ru.pkg.UserVoteTestData.MATCHER;

public class UserVoteServiceMockitoTest extends AbstractServiceMockitoTest {

    @Autowired
    UserVoteService service;

    @Autowired
    UserVoteRepository repository;

    @Before
    public void setUp() throws Exception {
        reset(repository);
    }

    @Test
    public void testSave() throws Exception {
        UserVote userVoteToUse = new UserVote(USER_1_ID, RESTAURANT_1_ID);
        when(repository.save(userVoteToUse)).thenReturn(userVoteToUse);
        service.save(userVoteToUse);
        verify(repository).save(userVoteToUse);
    }

    @Test(expected = VotingException.class)
    public void testSaveUserNotFound() throws Exception {
        doThrow(new DataIntegrityViolationException("")).when(repository).save(VOTE_USER_NOT_FOUND);
        service.save(VOTE_USER_NOT_FOUND);
    }

    @Test
    public void testFindById() throws Exception {
        when(repository.findById(USER_1_ID)).thenReturn(USER_1_VOTE);
        MATCHER.assertEquals(USER_1_VOTE, service.findById(USER_1_ID));
        verify(repository).findById(USER_1_ID);
    }

    @Test(expected = VotingException.class)
    public void testFindByIdNotFound() throws Exception {
        when(repository.findById(UserTestData.NOT_FOUND_INDEX)).thenReturn(null);
        service.findById(UserTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testFindAll() throws Exception {
        when(repository.findAll()).thenReturn(ALL_RESTAURANTS_VOTES);
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS_VOTES, service.findAll());
    }

    @Test
    public void testDelete() throws Exception {
        when(repository.delete(USER_1_ID)).thenReturn(true);
        service.delete(USER_1_ID);
        verify(repository).delete(USER_1_ID);
    }

    @Test(expected = VotingException.class)
    public void testDeleteNotFound() throws Exception {
        when(repository.delete(UserTestData.NOT_FOUND_INDEX)).thenReturn(false);
        service.delete(UserTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testReset() throws Exception {
        service.reset();
        verify(repository).reset();
    }
}
