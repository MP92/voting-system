package ru.pkg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.to.UserTO;
import ru.pkg.utils.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.pkg.utils.UserUtil.createFromTO;
import static ru.pkg.utils.UserUtil.updateFromTO;


@Service
public class UserServiceImpl implements UserService {

    private static final String EXCEPTION_MSG_PATTERN = "User with id=%d not found";

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository repository;

    @Override
    public User findById(int id) throws UserNotFoundException {
        LOG.debug("findById {}", id);

        User user = repository.findById(id);
        if (user == null) {
            throw new UserNotFoundException(String.format(EXCEPTION_MSG_PATTERN, id));
        }
        return user;
    }

    @Override
    public void add(User user) {
        LOG.debug("add {}", user);

        repository.save(user);
    }

    public void add(UserTO to) {
        add(createFromTO(to));
    }

    @Override
    public void update(User user) throws UserNotFoundException {
        LOG.debug("update {}", user);

        if (repository.save(user) == null) {
            throw new UserNotFoundException(String.format(EXCEPTION_MSG_PATTERN, user.getId()));
        }
    }

    public void update(UserTO to) throws UserNotFoundException {
        update(updateFromTO(findById(to.getId()), to));
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        LOG.debug("delete user with id={}", id);

        if (!repository.delete(id)) {
            throw new UserNotFoundException(String.format(EXCEPTION_MSG_PATTERN, id));
        }
    }

    @Override
    public Collection<User> findAll() {
        LOG.debug("findAll");

        return repository.findAll();
    }

    @Override
    @Transactional
    public void markAsVotedToday(int id) {
        User user = findById(id);
        user.setLastVoted(LocalDateTime.now());
        update(user);
    }
}
