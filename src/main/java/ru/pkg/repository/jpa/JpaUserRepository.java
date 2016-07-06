package ru.pkg.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    public User save(User user) {
        checkModificationAllowed(user.getId());
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            User byId = findById(user.getId());
            return byId != null ? em.merge(user) : null;
        }
    }

    public User findById(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByName(String name) {
        return DataAccessUtils.singleResult(em.createQuery("SELECT u FROM User u WHERE u.name=:name", User.class).setParameter("name", name).getResultList());
    }

    public List<User> findAll() {
        return em.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.registered", User.class).getResultList();
    }

    public boolean delete(int id) {
        checkModificationAllowed(id);
        Query query = em.createQuery("DELETE FROM User user WHERE user.id=:id");
        return query.setParameter("id", id).executeUpdate() > 0;
    }
}
