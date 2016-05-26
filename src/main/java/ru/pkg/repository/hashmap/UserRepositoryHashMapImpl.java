package ru.pkg.repository.hashmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepositoryHashMapImpl implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryHashMapImpl.class);

    private AtomicInteger counter = new AtomicInteger(0);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    {
        save(new User(null, "Admin", "admin", Role.ROLE_ADMIN));
        save(new User(null, "User", "user", Role.ROLE_USER));
    }

    @PostConstruct
    public void init() {
        LOG.info("\t init");
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("\t preDestroy");
    }

    @Override
    public User findById(int id) {
        LOG.debug("findById {}", id);
        return repository.get(id);
    }

    @Override
    public boolean save(User user) {
        LOG.debug("save {}", user);

        if (user == null) {
            return false;
        }

        int id = user.isNew() ? counter.getAndIncrement() : user.getId();

        if (!user.isNew() && (findById(id) == null)) {
            return false;
        }

        repository.put(id, user);

        return true;
    }

    @Override
    public boolean delete(int id) {
        LOG.debug("delete id={}", id);
        return repository.remove(id) != null;
    }

    @Override
    public Collection<User> findAll() {
        LOG.debug("findAll");
        return repository.values();
    }

    public void clear() {
        LOG.debug("clear");
        repository.clear();
        counter.set(0);
    }
}
