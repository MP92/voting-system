package ru.pkg.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.service.UserService;
import ru.pkg.web.AbstractControllerTest;

public abstract class AbstractUserControllerTest extends AbstractControllerTest {

    @Autowired
    UserService service;
}
