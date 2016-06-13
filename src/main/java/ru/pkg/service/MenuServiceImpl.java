package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Menu;
import ru.pkg.repository.MenuRepository;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository repository;

    @Override
    public void add(int restaurantId, int dishId) {
        repository.save(restaurantId, dishId);
    }

    @Override
    public void add(Menu menu) {
        if (!menu.isEmpty()) {
            repository.save(menu);
        }
    }

    @Transactional
    @Override
    public void replace(Menu menu) {
        repository.deleteAll(menu.getRestaurantId());
        if (!menu.isEmpty()) {
            repository.save(menu);
        }
    }

    @Override
    public List<Menu> findAll() {
        return repository.findAll();
    }

    @Override
    public Menu findById(int restaurantId) {
        return repository.findById(restaurantId);
    }

    @Override
    public void delete(int restaurantId, int dishId) {
        repository.delete(restaurantId, dishId);
    }

    @Override
    public void deleteAll(int restaurantId) {
        repository.deleteAll(restaurantId);
    }
}
