package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.pkg.model.Dish;
import ru.pkg.repository.DishRepository;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

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

    @Override
    public void update(Dish dish) throws DishNotFoundException {
        if (repository.save(dish) == null) {
            throw new DishNotFoundException(dish);
        }
    }

    @Override
    public void delete(int id, int restaurantId) throws DishNotFoundException {
        if (!repository.delete(id, restaurantId)) {
            throw new DishNotFoundException(id, restaurantId);
        }
    }
}
