package ru.pkg.web.user;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.test.web.servlet.MvcResult;
import ru.pkg.TestUtils;
import ru.pkg.model.User;
import ru.pkg.testdata.UserTestData;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasProperty;
import static ru.pkg.testdata.UserTestData.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.hamcrest.Matchers.*;

import static ru.pkg.web.user.JspAdminController.MESSAGE_FORMAT;

public class JspAdminControllerTest extends AbstractUserControllerTest {

    private static final String ADMIN_ID = String.valueOf(UserTestData.ADMIN_ID);
    private static final String NOT_FOUND_INDEX = String.valueOf(UserTestData.NOT_FOUND_INDEX);

    @Test
    public void testCreate() throws Exception {
        MvcResult result = mockMvc.perform(post("/admin/users/save")
                            .param("name", NEW_USER.getName())
                            .param("surname", NEW_USER.getSurname())
                            .param("password", NEW_USER.getPassword()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/users"))
                .andExpect(redirectedUrl("/admin/users"))
                .andExpect(flash().attribute("message", String.format(MESSAGE_FORMAT, CREATED_USER_ID, "has been created")))
                .andReturn();

        User created = service.findById(CREATED_USER_ID);
        Assert.assertEquals(NEW_USER.getName(), created.getName());
        Assert.assertEquals(NEW_USER.getSurname(), created.getSurname());
        Assert.assertEquals(NEW_USER.getPassword(), created.getPassword());
    }

    @Test
    public void testUpdate() throws Exception {
        MvcResult result = mockMvc.perform(post("/admin/users/save")
                            .param("id", ADMIN_ID)
                            .param("name", NEW_USER.getName())
                            .param("surname", NEW_USER.getSurname())
                            .param("password", NEW_USER.getPassword()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/users"))
                .andExpect(redirectedUrl("/admin/users"))
                .andExpect(flash().attribute("message", String.format(MESSAGE_FORMAT, UserTestData.ADMIN_ID, "has been updated")))
                .andReturn();

        User updated = service.findById(UserTestData.ADMIN_ID);
        Assert.assertEquals(NEW_USER.getName(), updated.getName());
        Assert.assertEquals(NEW_USER.getSurname(), updated.getSurname());
        Assert.assertEquals(NEW_USER.getPassword(), updated.getPassword());
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
         mockMvc.perform(post("/admin/users/save")
                    .param("id", NOT_FOUND_INDEX)
                    .param("name", NEW_USER.getName())
                    .param("surname", NEW_USER.getSurname())
                    .param("password", NEW_USER.getPassword()))
                 .andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(view().name("user/userForm"))
                 .andExpect(forwardedUrl("/WEB-INF/jsp/user/userForm.jsp"))
                 .andExpect(model().attributeHasFieldErrors("user", "id"))
                 .andExpect(model().attribute("user", hasProperty("id", samePropertyValuesAs(UserTestData.NOT_FOUND_INDEX))))
                 .andExpect(model().attribute("user", hasProperty("name", is(NEW_USER.getName()))))
                 .andExpect(model().attribute("user", hasProperty("surname", is(NEW_USER.getSurname()))))
                 .andExpect(model().attribute("user", hasProperty("password", is(NEW_USER.getPassword()))));
    }

    @Test
    public void testSaveFieldErrors() throws Exception {
        String name = TestUtils.createStringWithLength(2);
        String surname = TestUtils.createStringWithLength(26);
        String password = TestUtils.createStringWithLength(4);
        mockMvc.perform(post("/admin/users/save")
                        .param("name", name)
                        .param("surname", surname)
                        .param("password", password))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/userForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/user/userForm.jsp"))
                .andExpect(model().attributeHasFieldErrors("user", "name"))
                .andExpect(model().attributeHasFieldErrors("user", "surname"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attribute("user", hasProperty("id", nullValue())))
                .andExpect(model().attribute("user", hasProperty("name", is(name))))
                .andExpect(model().attribute("user", hasProperty("surname", is(surname))))
                .andExpect(model().attribute("user", hasProperty("password", is(password))));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/admin/users/delete").param("id", ADMIN_ID))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/users"))
                .andExpect(redirectedUrl("/admin/users"))
                .andExpect(flash().attribute("message", is(String.format(MESSAGE_FORMAT, UserTestData.ADMIN_ID, "has been deleted"))));

        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), service.findAll());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(get("/admin/users/delete").param("id", NOT_FOUND_INDEX))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/users"))
                .andExpect(redirectedUrl("/admin/users"))
                .andExpect(flash().attribute("message", is(String.format(MESSAGE_FORMAT, UserTestData.NOT_FOUND_INDEX, "not found"))));

        MATCHER.assertCollectionsEquals(ALL_USERS, service.findAll());
    }

    @Test
    public void testInitCreateForm() throws Exception {
        mockMvc.perform(get("/admin/users/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/userForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/user/userForm.jsp"))
                .andExpect(model().attribute("user", hasProperty("id", nullValue())))
                .andExpect(model().attribute("user", hasProperty("name", isEmptyOrNullString())))
                .andExpect(model().attribute("user", hasProperty("surname", isEmptyOrNullString())))
                .andExpect(model().attribute("user", hasProperty("password", isEmptyOrNullString())));
    }

    @Test
    public void testInitEditForm() throws Exception {
        mockMvc.perform(get("/admin/users/edit").param("id", ADMIN_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/userForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/user/userForm.jsp"))
                .andExpect(model().attribute("user", hasProperty("id", is(ADMIN.getId()))))
                .andExpect(model().attribute("user", hasProperty("name", is(ADMIN.getName()))))
                .andExpect(model().attribute("user", hasProperty("surname", is(ADMIN.getSurname()))))
                .andExpect(model().attribute("user", hasProperty("password", is(ADMIN.getPassword()))));
    }

    @Test
    public void testShowDetails() throws Exception {
        mockMvc.perform(get("/admin/users/details").param("id", ADMIN_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/userDetails"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/user/userDetails.jsp"))
                .andExpect(model().attribute("user", is(ADMIN_WITH_VOTE)));
    }

    @Test
    public void testShowList() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/userList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/user/userList.jsp"))
                .andExpect(model().attribute("userList", hasSize(USERS_COUNT)))
                .andExpect(model().attribute("userList", is(ALL_USERS_WITH_VOTES)));
    }
}