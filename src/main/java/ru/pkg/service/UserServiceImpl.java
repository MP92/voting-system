package ru.pkg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.utils.exception.MissingUserIdException;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository repository;

    @Override
    public User findById(int id) {
        LOG.debug("findById {}", id);

        User user = repository.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @Override
    public void add(User user) {
        LOG.debug("add {}", user);

        user.setId(null);
        repository.save(user);
    }

    @Override
    public void update(User user) {
        LOG.debug("update {}", user);

        if (user.isNew()) {
            throw new MissingUserIdException(user);
        }

        if (repository.save(user) == null) {
            throw new UserNotFoundException(user.getId());
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug("delete id={}", id);

        if (!repository.delete(id)) {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public Collection<User> findAll() {
        LOG.debug("findAll");

        return repository.findAll();
    }
}
