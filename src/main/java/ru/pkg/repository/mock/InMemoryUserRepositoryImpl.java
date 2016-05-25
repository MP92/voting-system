package ru.pkg.repository.mock;

import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryUserRepositoryImpl implements UserRepository {

    private AtomicInteger counter = new AtomicInteger(0);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    {
        save(new User(null, "User", "user", Role.ROLE_USER));
        save(new User(null, "Admin", "admin", Role.ROLE_ADMIN));
    }

    @Override
    public User findById(int id) {
        return repository.get(id);
    }

    @Override
    public void save(User user) {

        Objects.requireNonNull(user);

        int id = user.isNew() ? counter.getAndIncrement() : user.getId();

        if (!user.isNew() && (findById(id) == null)) {
            throw new UserNotFoundException(id);
        }

        repository.put(id, user);
    }

    @Override
    public User delete(int id) {
        return repository.remove(id);
    }

    @Override
    public Collection<User> findAll() {
        return repository.values();
    }

    public void clear() {
        repository.clear();
        counter.set(0);
    }
}
