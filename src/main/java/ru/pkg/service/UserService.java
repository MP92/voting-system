package ru.pkg.service;

import ru.pkg.model.User;

import java.util.Collection;

public interface UserService {
    User findById(int id);

    void add(User user);

    void update(User user);

    void delete(int id);

    Collection<User> findAll();
}
