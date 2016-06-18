package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.AbstractTest;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
public abstract class AbstractServiceTest extends AbstractTest {

    @Autowired
    protected CacheManager cacheManager;
}
