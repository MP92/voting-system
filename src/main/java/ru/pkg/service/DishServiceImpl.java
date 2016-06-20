package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Dish;
import ru.pkg.repository.DishRepository;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "dishes")
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository repository;

    @CacheEvict(allEntries = true)
    public Dish add(Dish dish) throws RestaurantNotFoundException {
        try {
            return repository.save(dish);
        } catch (DataIntegrityViolationException e) {
            throw new RestaurantNotFoundException(e);
        }
    }

    @Override
    public Dish findById(int id, int restaurantId) throws DishNotFoundException {
        Dish dish = repository.findById(id, restaurantId);
        if (dish == null) {
            throw new DishNotFoundException(id, restaurantId);
        }
        return dish;
    }

    @Override
    public List<Dish> findAll(int restaurantId) {
        return repository.findAll(restaurantId);
    }

    @Cacheable
    public Map<Integer, List<Dish>> findInAllMenus() {
        return repository.findInAllMenus();
    }

    @Cacheable
    public List<Dish> findInMenu(int restaurantId) {
        return repository.findInMenu(restaurantId);
    }

    @CacheEvict(allEntries = true)
    public void update(Dish dish) throws DishNotFoundException {
        if (repository.save(dish) == null) {
            throw new DishNotFoundException(dish);
        }
    }

    @CacheEvict(cacheNames = {"dishes", "restaurants"}, allEntries = true)
    @Transactional
    public void changeInMenuState(int id, int restaurantId) {
        Dish dish = findById(id, restaurantId);
        dish.setInMenu(!dish.isInMenu());
        update(dish);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id, int restaurantId) throws DishNotFoundException {
        if (!repository.delete(id, restaurantId)) {
            throw new DishNotFoundException(id, restaurantId);
        }
    }
}
