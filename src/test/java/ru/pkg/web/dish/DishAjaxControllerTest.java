package ru.pkg.web.dish;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.pkg.model.Dish;
import ru.pkg.web.AbstractControllerTest;
import ru.pkg.web.restaurant.RestaurantAjaxController;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.pkg.TestUtils.*;
import static ru.pkg.testdata.DishTestData.*;
import static ru.pkg.testdata.DishTestData.TestDishFactory.newInstanceForCreate;
import static ru.pkg.testdata.DishTestData.TestDishFactory.newInstanceForUpdate;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;

import static ru.pkg.utils.EntityUtils.*;

@WithMockUser(roles={"ADMIN"})
public class DishAjaxControllerTest extends AbstractControllerTest {

    private static final String AJAX_URL = String.format(RestaurantAjaxController.AJAX_URL + "/%d/dishes/", RESTAURANT_1_ID);

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(newInstanceForCreate())).with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        Dish toUpdateDish = newInstanceForUpdate();
        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(toUpdateDish)).with(csrf()))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateDish, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4), dishService.findAll(RESTAURANT_1_ID));
        MATCHER.assertEquals(toUpdateDish, dishService.findById(R_1_DISH_1_ID, RESTAURANT_1_ID));
    }

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get(AJAX_URL + R_1_DISH_1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(R_1_DISH_1));
    }

    @Test
    @WithMockUser(roles={"USER"})
    public void testFindByIdForbidden() throws Exception {
        mockMvc.perform(get(AJAX_URL + R_1_DISH_1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get(AJAX_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(R_1_ALL_DISHES));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(AJAX_URL + R_1_DISH_1_ID).with(csrf()))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(R_1_AFTER_DELETE_DISHES, dishService.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testChangeInMenuState() throws Exception {
        boolean initialState = dishService.findById(R_1_DISH_1_ID, RESTAURANT_1_ID).isInMenu();

        mockMvc.perform(post(AJAX_URL + R_1_DISH_1_ID).with(csrf()))
                .andExpect(status().isOk());

        Assert.assertNotEquals(initialState, dishService.findById(R_1_DISH_1_ID, RESTAURANT_1_ID).isInMenu());
    }
}