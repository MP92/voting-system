package ru.pkg.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.pkg.model.User;
import ru.pkg.web.AbstractControllerTest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.pkg.testdata.UserTestData.*;
import static ru.pkg.TestUtils.*;

public class AdminRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = AdminRestController.REST_URL + "/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(ADMIN));
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID).with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + ADMIN_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testCreate() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                    .with(userHttpBasic(ADMIN))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(NEW_USER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        User user = readFromJson(resultActions, User.class);
        TestUser expected = new TestUser(user.getId(), NEW_USER);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, expected, USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testUpdate() throws Exception {
        TestUser toUpdateUser = new TestUser(ADMIN_ID, NEW_USER);
        mockMvc.perform(put(REST_URL + ADMIN_ID)
                            .with(userHttpBasic(ADMIN))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(toUpdateUser)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateUser, USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get(REST_URL).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(ALL_USERS));
    }
}