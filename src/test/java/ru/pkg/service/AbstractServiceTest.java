package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.pkg.AbstractTest;
import static ru.pkg.Profiles.ACTIVE_DB;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(value = "classpath:db/populateDB.sql")
@ActiveProfiles(ACTIVE_DB)
public abstract class AbstractServiceTest extends AbstractTest {

    @Autowired
    protected CacheManager cacheManager;
}
