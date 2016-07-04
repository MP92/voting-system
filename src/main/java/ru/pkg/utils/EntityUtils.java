package ru.pkg.utils;

import org.springframework.util.StringUtils;
import ru.pkg.model.*;
import ru.pkg.to.DishTO;
import ru.pkg.to.RestaurantTO;
import ru.pkg.to.UserTO;
import ru.pkg.to.VotingStatistics;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Convenient util methods for entities.
 */
public class EntityUtils {

    public static User createFromTO(UserTO userTO) {
        return new User(userTO.getId(), userTO.getName(), userTO.getSurname(), userTO.getPassword(), Role.ROLE_USER);
    }

    public static Restaurant createFromTO(RestaurantTO restaurantTO) {
        return new Restaurant(restaurantTO.getId(), restaurantTO.getName(), restaurantTO.getDescription(), restaurantTO.getAddress(), restaurantTO.getPhoneNumber());
    }

    public static Dish createFromTO(DishTO dishTO) {
        return new Dish(dishTO.getId(), dishTO.getName(), dishTO.getDescription(), dishTO.getWeight(), dishTO.getCategory(), dishTO.getPrice(), dishTO.isInMenu());
    }

    public static User updateFromTO(User user, UserTO userTO) {
        user.setName(userTO.getName());
        user.setSurname(userTO.getSurname());
        user.setPassword(userTO.getPassword());
        return prepareToSave(user);
    }

    public static UserTO asTO(User user) {
        return new UserTO(user.getId(), user.getName(), user.getSurname(), user.getPassword(), user.getLastVoted());
    }

    public static RestaurantTO asTO(Restaurant restaurant) {
        return new RestaurantTO(restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.getAddress(), restaurant.getPhoneNumber());
    }

    public static DishTO asTO(Dish dish) {
        return new DishTO(dish.getId(), dish.getName(), dish.getDescription(), dish.getWeight(), dish.getCategory(), dish.getPrice(), dish.isInMenu());
    }

    public static boolean isUserVotedJustNow(User user) {
        return ChronoUnit.MILLIS.between(user.getLastVoted(), LocalDateTime.now()) < 500;
    }

    public static List<VotingStatistics> getVotingStatistics(List<Restaurant> restaurants, List<UserVote> votes) {
        Map<Integer, Long> votesMap = votes.stream().collect(Collectors.groupingBy(UserVote::getRestaurantId, Collectors.counting()));
        long sumVotes = votesMap.values().stream().mapToLong(Number::longValue).sum();

        return restaurants.stream().map(r -> new VotingStatistics(r, votesMap.getOrDefault(r.getId(), 0L), sumVotes)).collect(Collectors.toList());
    }

    public static List<Integer> getDishIDs(List<Dish> dishList) {
        return dishList.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public static List<Dish> getFilteredByIDs(List<Dish> all, List<Integer> ids) {
        return all.stream().filter(dish -> ids.contains(dish.getId())).collect(Collectors.toList());
    }

    public static List<Restaurant> getWithMenus(List<Restaurant> restaurants, Map<Integer, List<Dish>> menuMap) {
        restaurants.forEach(r -> r.setMenu(menuMap.getOrDefault(r.getId(), Collections.emptyList())));
        return restaurants;
    }

    public static List<Integer> getRestaurantIDs(List<Restaurant> restaurants) {
        return restaurants.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public static <T extends NamedEntity> T prepareToSave(T namedEntity) {
        namedEntity.setName(StringUtils.capitalize(namedEntity.getName().toLowerCase()));
        return namedEntity;
    }

    public static User prepareToSave(User user) {
        user.setName(StringUtils.capitalize(user.getName().toLowerCase()));
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        return user;
    }
}
