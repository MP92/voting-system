package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.to.UserTO;
import ru.pkg.utils.exception.UserNotFoundException;
import ru.pkg.utils.exception.VotingException;

import java.util.List;

import static ru.pkg.utils.UserUtil.updateFromTO;

@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @CacheEvict(allEntries = true)
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

    @Cacheable
    public List<User> findAll() {
        return repository.findAll();
    }

    @CacheEvict(allEntries = true)
    public void update(User user) throws UserNotFoundException {
        if (repository.save(user) == null) {
            throw new UserNotFoundException(user);
        }
    }

    @CacheEvict(allEntries = true)
    public void update(UserTO to) throws UserNotFoundException {
        update(updateFromTO(findById(to.getId()), to));
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) throws UserNotFoundException {
        if (!repository.delete(id)) {
            throw new UserNotFoundException(id);
        }
    }

    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void saveVote(int userId, int restaurantId) throws VotingException {
        try {
            repository.saveVote(userId, restaurantId);
        } catch (DataIntegrityViolationException e) {
            throw new VotingException(e);
        }
    }

    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void deleteVote(int userId) throws VotingException {
        if (!repository.deleteVote(userId)) {
            throw new VotingException(userId);
        }
    }

    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void resetVotes() {
        repository.resetVotes();
    }
}
