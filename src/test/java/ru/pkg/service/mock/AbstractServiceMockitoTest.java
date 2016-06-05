package ru.pkg.service.mock;

import org.springframework.test.context.ContextConfiguration;
import ru.pkg.AbstractTest;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/repositories-mock.xml"
})
public abstract class AbstractServiceMockitoTest extends AbstractTest {
}
