package ru.pkg.service;

import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.repository.mock.InMemoryUserRepositoryImpl;

import java.util.Collection;

public class UserServiceImpl implements UserService {

    UserRepository repository = new InMemoryUserRepositoryImpl();

    @Override
    public User findById(int id) {
        return repository.findById(id);
    }

    @Override
    public void add(User user) {
        repository.save(user);
    }

    @Override
    public void update(User user) {
        repository.save(user);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public Collection<User> findAll() {
        return repository.findAll();
    }
}
