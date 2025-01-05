package eu.fischerserver.controlpage.controlpagebackend.integration.config;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
// ro-back changes after each test-method.
// see: https://docs.spring.io/spring-framework/reference/testing/integration.html#testing-tx, https://stackoverflow.com/a/58564769/23530724
@Transactional
// see: https://stackoverflow.com/questions/59372048/testcontainers-hikari-and-failed-to-validate-connection-org-postgresql-jdbc-pgc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class ControlpageIntegrationTestBase {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
}
