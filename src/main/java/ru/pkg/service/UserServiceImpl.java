package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.to.UserTO;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.List;

import static ru.pkg.utils.UserUtil.updateFromTO;

@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @CacheEvict(allEntries = true)
    public User add(User user) {
        return userRepository.save(user);
    }

    public User findById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @Cacheable
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @CacheEvict(allEntries = true)
    public void update(User user) throws UserNotFoundException {
        if (userRepository.save(user) == null) {
            throw new UserNotFoundException(user);
        }
    }

    @CacheEvict(allEntries = true)
    public void update(UserTO to) throws UserNotFoundException {
        update(updateFromTO(findById(to.getId()), to));
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) throws UserNotFoundException {
        if (!userRepository.delete(id)) {
            throw new UserNotFoundException(id);
        }
    }
}
