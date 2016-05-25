package ru.pkg.repository;

import ru.pkg.model.User;

import java.util.Collection;

public interface UserRepository {
    User findById(int id);

    boolean save(User user);

    boolean delete(int id);

    Collection<User> findAll();

    void clear();
}
