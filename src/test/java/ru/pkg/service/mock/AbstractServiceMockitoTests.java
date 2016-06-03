package ru.pkg.service.mock;

import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/repositories-mock.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractServiceMockitoTests {
    private final Logger logger = getLogger(getClass());

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {

        private void logInfo(Description description, String status, long nanos) {
            String testName = description.getMethodName();
            logger.info(String.format("Test %s %s, spent %d microseconds",
                    testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, "finished", nanos);
        }
    };
}
