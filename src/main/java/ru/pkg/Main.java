package ru.pkg;

import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pkg.service.UserService;

import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(
                        new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);

        appCtx.getEnvironment().setActiveProfiles(Profiles.POSTGRESQL, Profiles.JDBC);
        appCtx.refresh();

        System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

        UserService service = (UserService)appCtx.getBean("userServiceImpl");
        service.delete(1000);
    }
}
