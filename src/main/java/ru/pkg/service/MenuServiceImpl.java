package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Menu;
import ru.pkg.repository.MenuRepository;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository repository;


    @Override
    public void add(int dishId, int restaurantId) throws DishNotFoundException {
        try {
            repository.save(dishId, restaurantId);
        } catch (DataIntegrityViolationException e) {
            throw new DishNotFoundException(e);
        }
    }

    @Override
    public void add(Menu menu) throws DishNotFoundException {
        if (!menu.isEmpty()) {
            try {
                repository.save(menu);
            } catch (DataIntegrityViolationException e) {
                throw new DishNotFoundException(e);
            }
        }
    }

    @Transactional
    @Override
    public void replace(Menu menu) throws DishNotFoundException {
        repository.deleteAll(menu.getRestaurantId());
        if (!menu.isEmpty()) {
            try {
                repository.save(menu);
            } catch (DataIntegrityViolationException e) {
                throw new DishNotFoundException(e);
            }
        }
    }

    @Override
    public List<Menu> findAll() {
        return repository.findAll();
    }

    @Override
    public Menu findById(int restaurantId) throws RestaurantNotFoundException {
        Menu menu = repository.findById(restaurantId);
        if (menu == null) {
            throw new RestaurantNotFoundException(restaurantId);
        }
        return menu;
    }

    @Override
    public void delete(int dishId, int restaurantId) throws DishNotFoundException {
        if (!repository.delete(dishId, restaurantId)) {
            throw new DishNotFoundException(dishId, restaurantId);
        }
    }

    @Override
    public void deleteAll(int restaurantId) {
        repository.deleteAll(restaurantId);
    }
}
