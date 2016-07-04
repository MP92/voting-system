package ru.pkg.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.pkg.web.AbstractControllerTest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.pkg.TestUtils.*;
import static ru.pkg.testdata.UserTestData.*;
import static ru.pkg.utils.EntityUtils.*;

@WithMockUser(roles={"ADMIN"})
public class AdminAjaxControllerTest extends AbstractControllerTest {

    public static final String AJAX_URL = AdminAjaxController.AJAX_URL + "/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(AJAX_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(ADMIN));
    }

    @Test
    @WithMockUser(roles={"USER"})
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(AJAX_URL + ADMIN_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(AJAX_URL + ADMIN_ID))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(NEW_USER)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        TestUser toUpdateUser = new TestUser(ADMIN_ID, NEW_USER);
        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(toUpdateUser)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateUser, USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get(AJAX_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(ALL_USERS));
    }
}