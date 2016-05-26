package ru.pkg.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.pkg.Profiles.HASH_MAP;

@ActiveProfiles(HASH_MAP)
public class UserServiceHashMapTests extends AbstractUserServiceTests {
}
