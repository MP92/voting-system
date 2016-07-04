package ru.pkg.web;

import org.junit.Test;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.pkg.testdata.UserTestData.*;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testShowRestaurantTable() throws Exception {
        mockMvc.perform(get("/admin/restaurants"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testShowRestaurantCatalog() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testShowDishList() throws Exception {
        mockMvc.perform(get("/dishes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testShowUserList() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(formLogin("/spring_security_check").user(USER_1.getName()).password(USER_1.getPassword()))
                .andExpect(authenticated().withUsername("User"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants"));
    }

    @Test
    public void testLoginUnauthenticated() throws Exception {
        mockMvc.perform(formLogin("/spring_security_check").password("invalid"))
                .andExpect(unauthenticated());
    }
}
