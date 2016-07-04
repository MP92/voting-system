package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.pkg.LoggedUser;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.to.UserTO;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.List;

import static ru.pkg.utils.EntityUtils.updateFromTO;

@Service("userService")
@CacheConfig(cacheNames = "users")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository repository;

    @CacheEvict(allEntries = true)
    @Transactional
    public User add(User user) {
        return repository.save(user);
    }

    public User findById(int id) throws UserNotFoundException {
        User user = repository.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @Override
    public User findByName(String name) throws UserNotFoundException {
        User user = repository.findByName(name);
        if (user == null) {
            throw new UserNotFoundException(name);
        }
        return user;
    }

    @Cacheable
    public List<User> findAll() {
        return repository.findAll();
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void update(User user) throws UserNotFoundException {
        if (repository.save(user) == null) {
            throw new UserNotFoundException(user);
        }
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void update(UserTO to) throws UserNotFoundException {
        update(updateFromTO(findById(to.getId()), to));
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void delete(int id) throws UserNotFoundException {
        if (!repository.delete(id)) {
            throw new UserNotFoundException(id);
        }
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void changeEnabledState(int id) {
        User user = findById(id);
        user.setEnabled(!user.isEnabled());
        update(user);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return new LoggedUser(findByName(StringUtils.capitalize(name.toLowerCase())));
    }
}
