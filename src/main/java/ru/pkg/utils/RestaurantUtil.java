package ru.pkg.utils;

import ru.pkg.model.BaseEntity;
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;
import ru.pkg.to.RestaurantWithVotes;
import ru.pkg.to.VotingStatistics;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static List<RestaurantWithVotes> getWithVotes(List<Restaurant> restaurants, Map<Integer, Integer> votes) {
        return restaurants.stream().map(restaurant -> new RestaurantWithVotes(restaurant, votes.get(restaurant.getId()))).collect(Collectors.toList());
    }

    public static List<Restaurant> getWithMenus(List<Restaurant> restaurants, Map<Integer, List<Dish>> menuMap) {
        restaurants.forEach(r -> r.setMenu(menuMap.getOrDefault(r.getId(), Collections.emptyList())));
        return restaurants;
    }

    public static List<Integer> getIDs(List<Restaurant> restaurants) {
        return restaurants.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public static List<VotingStatistics> getStatistics(List<Restaurant> restaurants) {
        Double sumVotes = restaurants.stream().collect(Collectors.summingDouble(Restaurant::getVotes));
        return restaurants.stream().map(restaurant -> new VotingStatistics(restaurant.getId(), restaurant.getName(), restaurant.getVotes(), restaurant.getVotes() / sumVotes)).collect(Collectors.toList());
    }
}
