package ru.pkg;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.Restaurant;

public class RestaurantTestData {

    public static final int START_INDEX = 100;

    public static final int NOT_FOUND_INDEX = 100000;

    public static final ModelMatcher<Restaurant, String> MATCHER = new ModelMatcher<>(Restaurant::toString);

    public static final Restaurant TEST_RESTAURANT_1 = new Restaurant(START_INDEX, "E Pellicci", "E Pellicci description", "332 Bethnal Green Rd, London E2 0AG, England", "+44 20 7739 4873");
    public static final Restaurant TEST_RESTAURANT_2 = new Restaurant(START_INDEX + 1, "taNgia", "taNgia description", "108 Mitcham Road | Tooting Broadway, London SW17 9NG, England", "+44 20 3774 0779");

    public static final Restaurant TEST_RESTAURANT_NEW = new Restaurant(null, "new", "test", "test", "test");

    public static class TestRestaurant extends Restaurant {
        public TestRestaurant(Integer id, Restaurant restaurant) {
            super(id, restaurant.getName(), restaurant.getDescription(), restaurant.getAddress(), restaurant.getPhoneNumber(), restaurant.getMenu());
        }
    }
}
