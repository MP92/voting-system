package ru.pkg.repository.hashmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.pkg.UserTestData.*;

@Repository
public class HashMapUserRepository implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HashMapUserRepository.class);

    private AtomicInteger counter = new AtomicInteger(START_INDEX);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    {
        save(new TestUser(null, ADMIN).asUser());
        save(new TestUser(null, USER).asUser());
    }

    @Override
    public User save(User user) {
        LOG.debug("save {}", user);

        if (user == null || (!user.isNew() && findById(user.getId()) == null)) {
            return null;
        }
        if (user.isNew()) {
            user.setId(counter.getAndIncrement());
        }
        repository.put(user.getId(), user);

        return user;
    }

    @Override
    public User findById(int id) {
        LOG.debug("findById {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> findAll() {
        LOG.debug("findAll");
        return repository.values().stream().sorted((u1, u2) -> {
            int result = u1.getName().compareToIgnoreCase(u2.getName());
            return result != 0 ? result : u1.getRegistered().compareTo(u2.getRegistered());
        }).collect(Collectors.toList());
    }

    @Override
    public boolean delete(int id) {
        LOG.debug("delete id={}", id);
        return repository.remove(id) != null;
    }

    public void clear() {
        LOG.debug("clear");
        repository.clear();
        counter.set(START_INDEX);
    }
}
