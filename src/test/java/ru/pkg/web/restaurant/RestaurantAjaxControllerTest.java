package ru.pkg.web.restaurant;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.pkg.model.Restaurant;
import ru.pkg.web.AbstractControllerTest;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.pkg.TestUtils.*;
import static ru.pkg.TestUtils.jsonMatcher;
import static ru.pkg.testdata.RestaurantTestData.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.TestRestaurantFactory.newInstanceForCreate;
import static ru.pkg.testdata.RestaurantTestData.TestRestaurantFactory.newInstanceForUpdate;
import static ru.pkg.utils.RestaurantUtil.*;

public class RestaurantAjaxControllerTest extends AbstractControllerTest {

    private static final String AJAX_URL = RestaurantAjaxController.AJAX_URL + "/";

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testCreate() throws Exception {
         mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(newInstanceForCreate())))
                 .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles={"USER"})
    public void testCreateForbidden() throws Exception {
        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(newInstanceForCreate())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testUpdate() throws Exception {
        Restaurant toUpdateRestaurant = newInstanceForUpdate();

        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(toUpdateRestaurant)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(RESTAURANT_2, toUpdateRestaurant), restaurantService.findAll());
    }

    @Test
    @WithMockUser(roles={"USER"})
    public void testUpdateForbidden() throws Exception {
        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(newInstanceForUpdate())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles={"USER"})
    public void testFindById() throws Exception {
        mockMvc.perform(get(AJAX_URL + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(RESTAURANT_1));
    }

    @Test
    public void testFindByIdUnauth() throws Exception {
        mockMvc.perform(get(AJAX_URL + RESTAURANT_1_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testFindAll() throws Exception {
        mockMvc.perform(get(AJAX_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(ALL_RESTAURANTS));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testDelete() throws Exception {
        mockMvc.perform(delete(AJAX_URL + RESTAURANT_1_ID))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.singletonList(RESTAURANT_2), restaurantService.findAll());
    }

    @Test
    @WithMockUser(roles={"USER"})
    public void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(AJAX_URL + RESTAURANT_1_ID))
                .andExpect(status().isForbidden());
    }
}