package ru.pkg.utils;

import ru.pkg.model.BaseEntity;
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static List<Restaurant> getWithMenus(List<Restaurant> restaurants, Map<Integer, List<Dish>> menuMap) {
        restaurants.forEach(r -> r.setMenu(menuMap.getOrDefault(r.getId(), Collections.emptyList())));
        return restaurants;
    }

    public static List<Integer> getIDs(List<Restaurant> restaurants) {
        return restaurants.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
}
