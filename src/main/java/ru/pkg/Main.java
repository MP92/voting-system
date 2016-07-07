package ru.pkg;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml"}, false);
        appCtx.getEnvironment().setActiveProfiles(Profiles.JDBC, Profiles.H2);
        appCtx.refresh();
    }
}
