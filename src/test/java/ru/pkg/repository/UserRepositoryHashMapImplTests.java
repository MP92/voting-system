package ru.pkg.repository;

import org.springframework.test.context.ActiveProfiles;

import static ru.pkg.Profiles.HASH_MAP;

@ActiveProfiles(HASH_MAP)
public class UserRepositoryHashMapImplTests extends AbstractUserRepositoryTests {
}
