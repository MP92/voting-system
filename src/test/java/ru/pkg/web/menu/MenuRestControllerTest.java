package ru.pkg.web.menu;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.pkg.web.AbstractControllerTest;
import ru.pkg.web.restaurant.RestaurantRestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static ru.pkg.testdata.DishTestData.R_1_DISH_1_ID;
import static ru.pkg.testdata.MenuTestData.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.TestUtils.*;

public class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = String.format(RestaurantRestController.REST_URL + "/%d/menu/", RESTAURANT_1_ID);

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonMatcher(R_1_MENU));
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get(RestaurantRestController.REST_URL + "/menus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonMatcher(ALL_MENUS));
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(R_1_TO_PUT_MENU)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(R_1_AFTER_REPLACE_MENU, menuService.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + R_1_DISH_1_ID))
                .andExpect(status().isOk());

        MATCHER.assertEquals(R_1_AFTER_DELETE_MENU, menuService.findById(RESTAURANT_1_ID));
    }
}