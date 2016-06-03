package ru.pkg.service;

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

    @Autowired
    UserRepository repository;

    @Override
    public User findById(int id) throws UserNotFoundException {
        User user = repository.findById(id);
        if (user == null) {
            throw new UserNotFoundException(String.format(EXCEPTION_MSG_PATTERN, id));
        }
        return user;
    }

    @Override
    public User add(User user) {
        return repository.save(user);
    }

    @Override
    public void update(User user) throws UserNotFoundException {
        if (repository.save(user) == null) {
            throw new UserNotFoundException(String.format(EXCEPTION_MSG_PATTERN, user.getId()));
        }
    }

    @Transactional
    public void update(UserTO to) throws UserNotFoundException {
        update(updateFromTO(findById(to.getId()), to));
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        if (!repository.delete(id)) {
            throw new UserNotFoundException(String.format(EXCEPTION_MSG_PATTERN, id));
        }
    }

    @Override
    public Collection<User> findAll() {
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
