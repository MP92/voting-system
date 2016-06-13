package ru.pkg.utils;

import ru.pkg.model.BaseEntity;
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;
import ru.pkg.to.RestaurantWithVotes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static List<RestaurantWithVotes> getWithVotes(List<Restaurant> restaurantList, Map<Integer, Integer> votes) {
        return restaurantList.stream().map(restaurant -> new RestaurantWithVotes(restaurant, votes.get(restaurant.getId()))).collect(Collectors.toList());
    }

    public static List<Restaurant> getWithMenus(List<Restaurant> restaurantList, Map<Integer, List<Dish>> menuMap) {
        restaurantList.forEach(r -> r.setMenu(menuMap.getOrDefault(r.getId(), Collections.emptyList())));
        return restaurantList;
    }

    public static List<Integer> getIDs(List<Restaurant> restaurantList) {
        return restaurantList.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
}
