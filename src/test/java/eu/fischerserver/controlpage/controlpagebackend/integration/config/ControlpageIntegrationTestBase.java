package eu.fischerserver.controlpage.controlpagebackend.integration.config;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
// ro-back changes after each test-method.
// see: https://docs.spring.io/spring-framework/reference/testing/integration.html#testing-tx, https://stackoverflow.com/a/58564769/23530724
@Transactional
public abstract class ControlpageIntegrationTestBase {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    static {
        postgres.start();
    }
}
