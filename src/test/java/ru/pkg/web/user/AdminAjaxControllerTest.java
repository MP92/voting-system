package ru.pkg.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.pkg.web.AbstractControllerTest;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.pkg.TestUtils.*;
import static ru.pkg.testdata.UserTestData.*;
import static ru.pkg.utils.EntityUtils.*;
import static ru.pkg.web.AbstractControllerTest.ROLE_ADMIN;
import static ru.pkg.utils.constants.ControllerConstants.PATH_AJAX_USER_LIST;

@WithMockUser(roles={ROLE_ADMIN})
public class AdminAjaxControllerTest extends AbstractControllerTest {

    private static final String AJAX_URL = PATH_AJAX_USER_LIST + "/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(AJAX_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(ADMIN));
    }

    @Test
    @WithMockUser(roles={ROLE_USER})
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(AJAX_URL + ADMIN_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(AJAX_URL + ADMIN_ID).with(csrf()))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), userService.findAll());
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(NEW_USER)).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        TestUser toUpdateUser = new TestUser(ADMIN_ID, NEW_USER);
        mockMvc.perform(withParamsFromBean(post(AJAX_URL), asTO(toUpdateUser)).with(csrf()))
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