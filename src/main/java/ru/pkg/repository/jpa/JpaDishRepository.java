package ru.pkg.repository.jpa;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.DishRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaDishRepository implements DishRepository {

    @PersistenceContext
    private EntityManager em;

    public Dish save(Dish dish, int restaurantId) throws DataIntegrityViolationException {
        if (dish.isNew() && em.find(Restaurant.class, restaurantId) == null || !dish.isNew() && findById(dish.getId(), restaurantId) == null) {
            return null;
        }

        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        if (dish.isNew()) {
            em.persist(dish);
            return dish;
        } else {
            return em.merge(dish);
        }
    }

    public Dish findById(int id, int restaurantId) {
        Dish dish = em.find(Dish.class, id);
        if (dish == null || dish.getRestaurant().getId() != restaurantId) {
            return null;
        }
        return dish;
    }

    public List<Dish> findAll(int restaurantId) {
        Query query = em.createQuery("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.id");
        return query.setParameter("restaurantId", restaurantId).getResultList();
    }

    public boolean delete(int id, int restaurantId) {
        Query query = em.createQuery("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId");
        return query.setParameter("id", id).setParameter("restaurantId", restaurantId).executeUpdate() > 0;
    }
}
