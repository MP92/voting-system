package ru.pkg.utils.constants;

public interface ControllerConstants {
    String PATH_USER_LIST = "/users";
    String PATH_DISH_LIST = "/dishes";
    String PATH_RESTAURANT_CATALOG = "/restaurants";
    String PATH_RESTAURANT_TABLE = "/admin/restaurants";
    String PATH_REGISTER = "/register";
    String PATH_LOGIN = "/login";
    String PATH_PROFILE = "/profile";

    String VIEW_NAME_USER_LIST = "user/userList";
    String VIEW_NAME_DISH_LIST = "dish/dishList";
    String VIEW_NAME_RESTAURANT_TABLE = "restaurant/restaurantTable";
    String VIEW_NAME_RESTAURANT_CATALOG = "restaurant/restaurantCatalog";
    String VIEW_NAME_LOGIN = "login";
    String VIEW_NAME_PROFILE = "profile";

    String PATH_VAR_ID = "id";
    String PATH_VAR_RESTAURANT_ID = "restaurantId";
    String PATH_ID = "/{" + PATH_VAR_ID + "}";
    String PATH_RESTAURANT_ID = "/{" + PATH_VAR_RESTAURANT_ID + "}";

    String PATH_AJAX_USER_LIST = "/ajax/users";
    String PATH_REST_USER_LIST = "/rest/users";
    String PATH_AJAX_RESTAURANT_LIST = "/ajax/restaurants";
    String PATH_REST_RESTAURANT_LIST = "/rest/restaurants";
    String PATH_AJAX_DISH_LIST = PATH_AJAX_RESTAURANT_LIST + PATH_RESTAURANT_ID + "/dishes";
    String PATH_REST_DISH_LIST = PATH_REST_RESTAURANT_LIST + PATH_RESTAURANT_ID + "/dishes";
    String PATH_VOTING_STATISTICS = "/voting";
    String PATH_VOTE = PATH_RESTAURANT_ID + "/vote";
    String PATH_VOTE_CANCEL = PATH_VOTING_STATISTICS + "/cancel";
    String PATH_VOTE_RESET = PATH_VOTING_STATISTICS + "/reset";
    String PATH_AJAX_PROFILE = "/ajax/profile";
    String PATH_REST_PROFILE = "/rest/profile";

    String ATTRIBUTE_USER = "user";
    String PARAM_ERROR = "error";

    static String redirectTo(String path) {
        return "redirect:" + (path.startsWith("/") ? path.substring(1) : path);
    }
}