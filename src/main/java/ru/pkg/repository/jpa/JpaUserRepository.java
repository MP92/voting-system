package ru.pkg.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Restaurant;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User save(User user) {
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

    public List<User> findAll() {
        return em.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.registered").getResultList();
    }

    @Transactional
    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM User user WHERE user.id=:id");
        return query.setParameter("id", id).executeUpdate() > 0;
    }
}
