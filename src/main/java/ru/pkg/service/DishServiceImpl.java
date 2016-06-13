package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.pkg.model.Dish;
import ru.pkg.repository.DishRepository;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Map;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository repository;

    @Override
    public Dish add(Dish dish) throws RestaurantNotFoundException {
        try {
            return repository.save(dish);
        } catch (DataIntegrityViolationException e) {
            throw new RestaurantNotFoundException(e);
        }
    }

    @Override
    public Dish findById(int restaurantId, int dishId) throws DishNotFoundException {
        Dish dish = repository.findById(restaurantId, dishId);
        if (dish == null) {
            throw new DishNotFoundException(dishId, restaurantId);
        }
        return dish;
    }

    @Override
    public List<Dish> findAll(int restaurantId) {
        return repository.findAll(restaurantId);
    }

    @Override
    public Map<Integer, List<Dish>> findInAllMenus() {
        return repository.findInAllMenus();
    }

    @Override
    public List<Dish> findInMenu(int restaurantId) {
        return repository.findInMenu(restaurantId);
    }

    @Override
    public void update(Dish dish) throws DishNotFoundException {
        if (repository.save(dish) == null) {
            throw new DishNotFoundException(dish);
        }
    }

    @Override
    public void delete(int restaurantId, int dishId) throws DishNotFoundException {
        if (!repository.delete(restaurantId, dishId)) {
            throw new DishNotFoundException(dishId, restaurantId);
        }
    }
}
