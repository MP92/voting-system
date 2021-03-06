package ru.pkg.testdata;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.Dish;
import ru.pkg.model.DishCategory;
import ru.pkg.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2;
import static ru.pkg.testdata.RestaurantTestData.TestRestaurantFactory;

import static ru.pkg.model.BaseEntity.START_SEQ;

public class DishTestData {

    public static final int R_1_DISH_1_ID = START_SEQ + 5;

    public static final int NOT_FOUND_INDEX = 100000;

    public static final ModelMatcher<Dish, String> MATCHER = new ModelMatcher<>(Dish::toString, Dish.class);

    public static final Dish R_1_DISH_1 = new Dish(
            R_1_DISH_1_ID, "Beer-and-Cheddar SoupBeer-and-Cheddar Soup",
            "When Jonathon Erdeljac opened his new restaurant, Jonathon's Oak Cliff, in Dallas, he knew he wanted to serve this rich soup. It's a favorite of his, especially with jalapenos and smoky bacon stirred in.",
            260, DishCategory.SOUP, 84.6, true);

    public static final Dish R_1_DISH_2 = new Dish(
            R_1_DISH_1_ID + 1, "Bucatini with Pancetta, Pecorino and Pepper",
            "\"I love knowing that I have leftovers in the refrigerator,\" says chef Shelley Lindgren. She'll often make pasta during the day like this rich, pancetta-studded bucatini that's tossed with plenty of freshly ground black pepper and Pecorino and then reheat a big bowl of it when she comes home from work late at night.",
            250, DishCategory.GARNISH, 75.4, true);

    public static final Dish R_1_DISH_3 = new Dish(
            R_1_DISH_1_ID + 2, "Cranberry Panna Cotta",
            "This elegant, low-fat panna cotta requires only five ingredients: cranberries, sugar, gelatin, water and buttermilk (instead of the usual cream)",
            200, DishCategory.DRINK, 65.4, true);

    public static final Dish R_1_DISH_4 = new Dish(
            R_1_DISH_1_ID + 3, "Boston Lettuce Salad with Herbs",
            "The herbed vinaigrette here would be lovely on any summer lettuces.",
            65, DishCategory.SALAD, 55.8, false);

    public static final Dish R_2_DISH_1 = new Dish(
            R_1_DISH_1_ID + 4, "Spicy Chickpea Soup",
            "Chickpeas are rich in both types of dietary fiber, which are important for maintaining heart health and for stabilizing blood sugar levels. Pam Anderson uses them as the base for this Indian-flavored creamy (though cream-free) soup, which she prepares by first pureeing it, then simmering it, to save time. \"Pureed beans give you richness without having to enrich the soup,\" she says.",
            265, DishCategory.SOUP, 150.5, true);

    public static final Dish R_2_DISH_2 = new Dish(
            R_1_DISH_1_ID + 5, "Chicken in Tarragon-Mustard Cream Sauce",
            "F&W's Grace Parisi steals the flavors from a classic French pan sauce (mustard, tarragon, white wine and cream) for this quick chicken saute.",
            230, DishCategory.GARNISH, 95.5, true);

    public static final Dish R_2_DISH_3 = new Dish(
            R_1_DISH_1_ID + 6, "Avocado, Orange and Jicama Salad",
            "Feta is a fun, briny addition to this salad's Mexican mix of jicama, avocado and cilantro.",
            70, DishCategory.SALAD, 65.5, true);

    public static final Dish R_2_DISH_4 = new Dish(
            R_1_DISH_1_ID + 7, "Равиоли из ананаса с тайским манго и лаймовым сорбетом",
            "Долго смотреть на этот десерт невозможно - он нереально, ну просто фантастически летний. Тончайшие ломтики ананасовой мякоти выполняют роль теста в классических равиоли. Внутри – нарезанное мелкими кубиками манго с соусом из лайма.",
            125, DishCategory.DESSERT, 245.5, false);

    static {
        R_1_DISH_1.setRestaurant(RESTAURANT_1);
        R_1_DISH_2.setRestaurant(RESTAURANT_1);
        R_1_DISH_3.setRestaurant(RESTAURANT_1);
        R_1_DISH_4.setRestaurant(RESTAURANT_1);
        R_2_DISH_1.setRestaurant(RESTAURANT_2);
        R_2_DISH_2.setRestaurant(RESTAURANT_2);
        R_2_DISH_3.setRestaurant(RESTAURANT_2);
        R_2_DISH_4.setRestaurant(RESTAURANT_2);
    }

    public static final List<Dish> R_1_ALL_DISHES = Arrays.asList(R_1_DISH_1, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4);
    public static final List<Dish> R_2_ALL_DISHES = Arrays.asList(R_2_DISH_1, R_2_DISH_2, R_2_DISH_3, R_2_DISH_4);

    public static final List<Dish> R_1_AFTER_DELETE_DISHES = Arrays.asList(R_1_DISH_2, R_1_DISH_3, R_1_DISH_4);

    public static class TestDishFactory {

        private static Dish newInstance(Integer id, Restaurant r) {
            Dish dish = new Dish(id, "Test", "test", 100, DishCategory.DESSERT, 123.45, false);
            dish.setRestaurant(r);
            return dish;
        }

        public static Dish newInstanceForCreate() {
            return newInstance(null, RESTAURANT_1);
        }

        public static Dish newInstanceForCreateForNonexistentRestaurant() {
            return newInstance(null, TestRestaurantFactory.newInstanceForUpdateNonexistent());
        }

        public static Dish newInstanceForUpdate() {
            return newInstance(R_1_DISH_1_ID, RESTAURANT_1);
        }

        public static Dish newInstanceForUpdateNonexistentDish() {
            return newInstance(NOT_FOUND_INDEX, RESTAURANT_1);
        }

        public static Dish newInstanceForUpdateForNonexistentRestaurant() {
            return newInstance(R_1_DISH_1_ID, TestRestaurantFactory.newInstanceForUpdateNonexistent());
        }

        public static Dish newInstanceForUpdateForeignDish() {
            return newInstance(R_1_DISH_1_ID, RESTAURANT_2);
        }
    }
}
