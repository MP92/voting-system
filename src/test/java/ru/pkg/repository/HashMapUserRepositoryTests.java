package ru.pkg.repository;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;

import static ru.pkg.UserTestData.*;

@ContextConfiguration("classpath:spring/HashMapUserRepository-context.xml")
public class HashMapUserRepositoryTests extends AbstractUserRepositoryTests {

    @Before
    public void prepareData() {
        repository.clear();
        repository.save(new TestUser(null, ADMIN));
        repository.save(new TestUser(null, USER));
    }
}
