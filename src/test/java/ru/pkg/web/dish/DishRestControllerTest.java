package ru.pkg.web.dish;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.pkg.model.Dish;
import ru.pkg.web.AbstractControllerTest;
import java.util.Arrays;

import static ru.pkg.TestUtils.*;
import static ru.pkg.testdata.DishTestData.*;
import static ru.pkg.testdata.DishTestData.R_1_DISH_4;
import static ru.pkg.testdata.DishTestData.TestDishFactory.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.UserTestData.ADMIN;
import static ru.pkg.testdata.UserTestData.USER_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.pkg.utils.constants.ControllerConstants.PATH_REST_RESTAURANT_LIST;

public class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = String.format(PATH_REST_RESTAURANT_LIST + "/%d/dishes/", RESTAURANT_1_ID);

    @Test
    public void testCreate() throws Exception {
        Dish newDish = newInstanceForCreate();
        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                    .with(userHttpBasic(ADMIN))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(newDish)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(newDish));

        newDish.setId(getIntFromJson(resultActions, "id"));
        MATCHER.assertCollectionsEquals(Arrays.asList(R_1_DISH_1, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4, newDish), dishService.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Dish toUpdateDish = newInstanceForUpdate();
        mockMvc.perform(put(REST_URL + R_1_DISH_1_ID)
                    .with(userHttpBasic(ADMIN))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(toUpdateDish)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateDish, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4), dishService.findAll(RESTAURANT_1_ID));

        MATCHER.assertEquals(toUpdateDish, dishService.findById(R_1_DISH_1_ID, RESTAURANT_1_ID));
    }

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get(REST_URL + R_1_DISH_1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(R_1_DISH_1));
    }

    @Test
    public void testFindByIdForbidden() throws Exception {
        mockMvc.perform(get(REST_URL + R_1_DISH_1_ID).with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get(REST_URL).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(R_1_ALL_DISHES));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + R_1_DISH_1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(R_1_AFTER_DELETE_DISHES, dishService.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testChangeInMenuState() throws Exception {
        boolean initialState = dishService.findById(R_1_DISH_1_ID, RESTAURANT_1_ID).isInMenu();

        mockMvc.perform(post(REST_URL + R_1_DISH_1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        Assert.assertNotEquals(initialState, dishService.findById(R_1_DISH_1_ID, RESTAURANT_1_ID).isInMenu());
    }
}