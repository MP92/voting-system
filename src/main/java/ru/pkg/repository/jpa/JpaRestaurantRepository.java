package ru.pkg.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return findById(restaurant.getId()) != null ? em.merge(restaurant) : null;
        }
    }

    public Restaurant findById(int id) {
        return DataAccessUtils.singleResult(em.createQuery("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu d WHERE (d.inMenu IS NULL OR d.inMenu=true) AND r.id=:id", Restaurant.class)
                .setParameter("id", id).getResultList());
    }

    public List<Restaurant> findAll() {
        return em.createQuery("SELECT r FROM Restaurant r ORDER BY r.name", Restaurant.class).getResultList();
    }

    public List<Restaurant> findAllWithMenu() {
        return em.createQuery("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu d WHERE (d.inMenu IS NULL OR d.inMenu=true) ORDER BY r.name", Restaurant.class).getResultList();
    }

    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM Restaurant r WHERE r.id=:id");
        return query.setParameter("id", id).executeUpdate() > 0;
    }
}
