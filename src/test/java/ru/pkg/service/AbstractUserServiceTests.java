package ru.pkg.service;

import org.junit.*;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.pkg.UserTestData.*;
import static ru.pkg.UserTestData.NEW_USER;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
//@Sql("classpath:db/populateDB.sql")
@Transactional
public abstract class AbstractUserServiceTests {

    private final Logger logger = getLogger(getClass());

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {

        private void logInfo(Description description, String status, long nanos) {
            String testName = description.getMethodName();
            logger.info(String.format("Test %s %s, spent %d microseconds",
                    testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, "finished", nanos);
        }
    };

    @Autowired
    protected UserService service;

    @Autowired
    protected UserRepository repository;
    
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
        User newUser = new TestUser(NEW_USER);
        service.add(newUser);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, newUser, USER), service.findAll());
    }
    
    @Test
    public void testUpdate() {
        TestUser newUser = new TestUser(ADMIN_ID, NEW_USER);
        service.update(newUser.asUser());
        MATCHER.assertCollectionsEquals(Arrays.asList(newUser, USER), service.findAll());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testUpdateNotFound() {
        User newUser = new TestUser(10000, NEW_USER);
        service.update(newUser);
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
