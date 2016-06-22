package ru.pkg.service.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.testdata.UserTestData;
import ru.pkg.model.UserVote;
import ru.pkg.repository.VotingRepository;
import ru.pkg.service.VotingService;
import ru.pkg.utils.exception.VotingException;

import static org.mockito.Mockito.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.UserTestData.USER_1_ID;
import static ru.pkg.testdata.UserVoteTestData.*;
import static ru.pkg.testdata.UserVoteTestData.MATCHER;

public abstract class VotingServiceMockitoTest extends AbstractServiceMockitoTest {

    @Autowired
    VotingService service;

    @Autowired
    VotingRepository repository;

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

    @Test
    public void testFindByIdNotFound() throws Exception {
        when(repository.findById(UserTestData.NOT_FOUND_INDEX)).thenReturn(null);
        MATCHER.assertEquals(EMPTY_VOTE, service.findById(UserTestData.NOT_FOUND_INDEX));
        verify(repository).findById(UserTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testFindAll() throws Exception {
        when(repository.findAll()).thenReturn(ALL_USER_VOTES);
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, service.findAll());
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
