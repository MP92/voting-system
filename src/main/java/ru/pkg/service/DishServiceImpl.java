package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Dish;
import ru.pkg.repository.DishRepository;
import ru.pkg.utils.exception.DishNotFoundException;

import java.util.List;

@Service
@CacheConfig(cacheNames = "dishes")
@Transactional(readOnly = true)
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository repository;

    @CacheEvict(cacheNames = {"dishes", "restaurants"}, allEntries = true)
    @Transactional
    public Dish add(Dish dish, int restaurantId) throws DataAccessException {
        return repository.save(dish, restaurantId);
    }

    @Cacheable
    public Dish findById(int id, int restaurantId) throws DishNotFoundException {
        Dish dish = repository.findById(id, restaurantId);
        if (dish == null) {
            throw new DishNotFoundException(id, restaurantId);
        }
        return dish;
    }

    @Cacheable
    public List<Dish> findAll(int restaurantId) {
        return repository.findAll(restaurantId);
    }

    @CacheEvict(cacheNames = {"dishes", "restaurants"}, allEntries = true)
    @Transactional
    public void update(Dish dish, int restaurantId) throws DishNotFoundException {
        if (repository.save(dish, restaurantId) == null) {
            throw new DishNotFoundException(dish, restaurantId);
        }
    }

    @CacheEvict(cacheNames = {"dishes", "restaurants"}, allEntries = true)
    @Transactional
    public void changeInMenuState(int id, int restaurantId) {
        Dish dish = findById(id, restaurantId);
        dish.setInMenu(!dish.isInMenu());
        update(dish, restaurantId);
    }

    @CacheEvict(cacheNames = {"dishes", "restaurants"}, allEntries = true)
    @Transactional
    public void delete(int id, int restaurantId) throws DishNotFoundException {
        if (!repository.delete(id, restaurantId)) {
            throw new DishNotFoundException(id, restaurantId);
        }
    }
}
