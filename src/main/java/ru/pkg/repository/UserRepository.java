package ru.pkg.repository;

import ru.pkg.model.User;
import java.util.List;

public interface UserRepository {

    User save(User user);

    User findById(int id);

    User findByName(String name);

    List<User> findAll();

    boolean delete(int id);
}
