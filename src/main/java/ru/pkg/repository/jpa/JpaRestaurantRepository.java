package ru.pkg.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return findById(restaurant.getId()) != null ? em.merge(restaurant) : null;
        }
    }

    public Restaurant findById(int id) {
        return em.find(Restaurant.class, id);
    }

    public List<Restaurant> findAll() {
        return em.createQuery("SELECT r FROM Restaurant r ORDER BY r.name").getResultList();
    }

    @Transactional
    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM Restaurant r WHERE r.id=:id");
        return query.setParameter("id", id).executeUpdate() > 0;
    }
}
