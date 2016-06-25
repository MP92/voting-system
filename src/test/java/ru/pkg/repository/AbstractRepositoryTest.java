package ru.pkg.repository;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.pkg.AbstractTest;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(value = "classpath:db/populateDB.sql")
public abstract class AbstractRepositoryTest extends AbstractTest {
}
