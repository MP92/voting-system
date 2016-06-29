package ru.pkg.repository;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.AbstractTest;
import static ru.pkg.Profiles.ACTIVE_DB;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
@ActiveProfiles(ACTIVE_DB)
public abstract class AbstractRepositoryTest extends AbstractTest {
}
