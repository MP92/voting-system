package ru.pkg;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.Menu;

import static ru.pkg.DishTestData.R_1_DISH_1_ID;
import static ru.pkg.DishTestData.R_2_DISH_1_ID;

import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.RestaurantTestData.RESTAURANT_2_ID;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuTestData {

    public static final ModelMatcher<Menu, String> MATCHER = new ModelMatcher<>(Menu::toString);

    public static final Menu R_1_MENU = new Menu(RESTAURANT_1_ID, Arrays.asList(R_1_DISH_1_ID, R_1_DISH_1_ID + 1, R_1_DISH_1_ID + 2));
    public static final Menu R_2_MENU = new Menu(RESTAURANT_2_ID, Arrays.asList(R_2_DISH_1_ID, R_2_DISH_1_ID + 1, R_2_DISH_1_ID + 2));

    public static final Menu R_1_AFTER_ADD_MENU = new Menu(RESTAURANT_1_ID, Arrays.asList(R_1_DISH_1_ID, R_1_DISH_1_ID + 1, R_1_DISH_1_ID + 2, R_1_DISH_1_ID + 3));

    public static final Menu R_1_AFTER_DELETE_MENU = new Menu(RESTAURANT_1_ID, Arrays.asList(R_1_DISH_1_ID + 1, R_1_DISH_1_ID + 2));

    public static final Menu R_1_EMPTY_MENU = new Menu(RESTAURANT_1_ID, Collections.emptyList());

    public static final Menu NOT_FOUND_MENU = new Menu(RestaurantTestData.NOT_FOUND_INDEX, Collections.emptyList());

    public static final List<Menu> ALL_MENUS = Arrays.asList(R_1_MENU, R_2_MENU);
}
