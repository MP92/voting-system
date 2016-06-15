package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.to.UserTO;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.List;

import static ru.pkg.utils.UserUtil.updateFromTO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) throws UserNotFoundException {
        if (userRepository.save(user) == null) {
            throw new UserNotFoundException(user);
        }
    }

    @Transactional
    public void update(UserTO to) throws UserNotFoundException {
        update(updateFromTO(findById(to.getId()), to));
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        if (!userRepository.delete(id)) {
            throw new UserNotFoundException(id);
        }
    }
}
