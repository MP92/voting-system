package ru.pkg.service;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;

import static ru.pkg.UserTestData.*;

@ContextConfiguration("classpath:spring/HashMapUserRepository-context.xml")
public class UserServiceHashMapTests extends AbstractUserServiceTests {

    @Before
    public void before() {
        repository.clear();
        repository.save(new TestUser(null, ADMIN));
        repository.save(new TestUser(null, USER));
    }
}
