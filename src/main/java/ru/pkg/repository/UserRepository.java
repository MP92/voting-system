package ru.pkg.repository;

import ru.pkg.model.BaseEntity;
import ru.pkg.model.User;
import ru.pkg.utils.exception.UserModificationDeniedException;
import java.util.List;

public interface UserRepository {

    User save(User user);

    User findById(int id);

    User findByName(String name);

    List<User> findAll();

    boolean delete(int id);

    default void checkModificationAllowed(Integer id) {
        if (id != null && id < BaseEntity.START_SEQ + 2) {
            throw new UserModificationDeniedException();
        }
    }
}
