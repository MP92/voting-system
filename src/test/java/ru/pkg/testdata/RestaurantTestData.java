package ru.pkg.testdata;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.pkg.testdata.DishTestData.*;

import static ru.pkg.testdata.UserVoteTestData.RESTAURANT_1_VOTES_COUNT;
import static ru.pkg.testdata.UserVoteTestData.RESTAURANT_2_VOTES_COUNT;

public class RestaurantTestData {

    public static final int START_INDEX = 100;

    public static final int RESTAURANT_1_ID = START_INDEX;
    public static final int RESTAURANT_2_ID = START_INDEX + 1;

    public static final int NEW_RESTAURANT_ID = START_INDEX + 10;

    public static final int NOT_FOUND_INDEX = 100000;

    public static final ModelMatcher<Restaurant, String> MATCHER = new ModelMatcher<>(Restaurant::toString);

    public static final List<Dish> R_1_IN_MENU_DISHES = Arrays.asList(R_1_DISH_1, R_1_DISH_2, R_1_DISH_3);
    public static final List<Dish> R_2_IN_MENU_DISHES = Arrays.asList(R_2_DISH_1, R_2_DISH_2, R_2_DISH_3);

    public static final Map<Integer, List<Dish>> ALL_MENUS = new HashMap<Integer, List<Dish>>() {
        {
            put(RESTAURANT_1_ID, R_1_IN_MENU_DISHES);
            put(RESTAURANT_2_ID, R_2_IN_MENU_DISHES);
        }
    };

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "E Pellicci", "E Pellicci description", "332 Bethnal Green Rd, London E2 0AG, England", "+44 20 7739 4873", RESTAURANT_1_VOTES_COUNT);
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID, "taNgia", "taNgia description", "108 Mitcham Road | Tooting Broadway, London SW17 9NG, England", "+44 20 3774 0779", RESTAURANT_2_VOTES_COUNT);

    public static final List<Restaurant> ALL_RESTAURANTS_WITHOUT_MENU = Arrays.asList(RESTAURANT_1, RESTAURANT_2);

    public static final List<Restaurant> ALL_RESTAURANTS_WITH_MENU = Arrays.asList(TestRestaurantFactory.newInstanceWithMenu(RESTAURANT_1, R_1_IN_MENU_DISHES), TestRestaurantFactory.newInstanceWithMenu(RESTAURANT_2, R_2_IN_MENU_DISHES));

    public static class TestRestaurantFactory {

        private static Restaurant newInstance(Integer id, int votes, List<Dish> menu) {
            return new Restaurant(id, "test", "test", "test", "test", votes, menu);
        }

        public static Restaurant newIntanceForCreate() {
            return newInstance(null, 0, null);
        }

        public static Restaurant newIntanceForUpdate() {
            return newInstance(RESTAURANT_1_ID, RESTAURANT_1_VOTES_COUNT, null);
        }

        public static Restaurant newIntanceForUpdateNonexistent() {
            return newInstance(NOT_FOUND_INDEX, 0, null);
        }

        public static Restaurant newInstanceWithoutMenu(Restaurant r) {
            return newInstanceWithMenu(r, null);
        }

        public static Restaurant newInstanceWithMenu(Restaurant r, List<Dish> menu) {
            return new Restaurant(r.getId(), r.getName(), r.getDescription(), r.getAddress(), r.getPhoneNumber(), r.getVotes(), menu);
        }
    }
}
