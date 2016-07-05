package ru.pkg.web.restaurant;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.pkg.model.Restaurant;
import ru.pkg.web.AbstractControllerTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static ru.pkg.TestUtils.*;
import static ru.pkg.testdata.RestaurantTestData.*;
import static ru.pkg.testdata.RestaurantTestData.TestRestaurantFactory.*;
import static ru.pkg.testdata.UserTestData.ADMIN;
import static ru.pkg.testdata.UserTestData.USER_1;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantRestController.REST_URL + "/";

    @Test
    public void testCreate() throws Exception {
        Restaurant toCreateRestaurant = newInstanceForCreate();
        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                    .with(userHttpBasic(ADMIN))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(toCreateRestaurant)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(toCreateRestaurant));

        toCreateRestaurant.setId(getIntFromJson(resultActions, "id"));
        List<Restaurant> expected = Arrays.asList(RESTAURANT_1, RESTAURANT_2, toCreateRestaurant);
        MATCHER.assertCollectionsEquals(expected, restaurantService.findAll());
    }

    @Test
    public void testCreateForbidden() throws Exception {
        mockMvc.perform(post(REST_URL)
                    .with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant toUpdateRestaurant = newInstanceForUpdate();
        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(toUpdateRestaurant)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(RESTAURANT_2, toUpdateRestaurant), restaurantService.findAll());
    }

    @Test
    public void testUpdateForbidden() throws Exception {
        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                    .with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_1_ID).with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(RESTAURANT_1));
    }

    @Test
    public void testFindByIdUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get(REST_URL).with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(ALL_RESTAURANTS_WITH_MENU));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.singletonList(RESTAURANT_2), restaurantService.findAll());
    }

    @Test
    public void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_1_ID).with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }
}