package ru.pkg.web.user;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.pkg.web.AbstractControllerTest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.pkg.testdata.UserTestData.*;
import static ru.pkg.TestUtils.*;

public class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + "/users/";

    @After
    public void tearDown() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonMatcher(ADMIN));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + ADMIN_ID))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testCreate() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(toJson(NEW_USER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonMatcher(NEW_USER));

        TestUser expected = new TestUser(getIntFromJson(resultActions, "id"), NEW_USER);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, expected, USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testUpdate() throws Exception {
        TestUser toUpdateUser = new TestUser(ADMIN_ID, NEW_USER);
        mockMvc.perform(put(REST_URL + ADMIN_ID)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(toJson(toUpdateUser)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateUser, USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonMatcher(ALL_USERS));
    }
}