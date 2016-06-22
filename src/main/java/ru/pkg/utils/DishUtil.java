package ru.pkg.utils;

import ru.pkg.model.BaseEntity;
import ru.pkg.model.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class DishUtil {

    public static List<Integer> getIDs(List<Dish> dishList) {
        return dishList.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public static List<Dish> getFilteredByIDs(List<Dish> all, List<Integer> ids) {
        return all.stream().filter(dish -> ids.contains(dish.getId())).collect(Collectors.toList());
    }
}
