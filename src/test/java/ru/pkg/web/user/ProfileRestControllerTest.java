package ru.pkg.web.user;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.pkg.LoggedUser;
import ru.pkg.web.AbstractControllerTest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.pkg.TestUtils.jsonMatcher;
import static ru.pkg.TestUtils.toJson;
import static ru.pkg.testdata.UserTestData.*;
import static ru.pkg.testdata.UserTestData.USER_1;
import static ru.pkg.testdata.UserTestData.USER_2;

public class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestController.REST_URL + "/";

    @After
    public void tearDown() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonMatcher(USER_1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER_2), userService.findAll());
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(NEW_USER)))
                .andExpect(status().isOk());

        TestUser expected = new TestUser(LoggedUser.getId(), NEW_USER);
        MATCHER.assertEquals(expected, userService.findById(LoggedUser.getId()));
    }
}