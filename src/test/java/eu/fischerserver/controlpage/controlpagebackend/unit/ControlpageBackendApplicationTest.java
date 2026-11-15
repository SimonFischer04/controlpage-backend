package eu.fischerserver.controlpage.controlpagebackend.unit;

import eu.fischerserver.controlpage.controlpagebackend.ControlpageBackendApplication;
import org.junit.jupiter.api.Test;

import static eu.fischerserver.controlpage.controlpagebackend.ControlpageBackendApplication.DEFAULT_PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControlpageBackendApplicationTest {
    private static int parsePort(String... args) {
        return ControlpageBackendApplication.parsePort(args);
    }

    @Test
    void testParsePortNumericFirstArgument() {
        assertEquals(8080, parsePort("8080"));
        assertEquals(0, parsePort("0"));
        assertEquals(65535, parsePort("65535"));
    }

    @Test
    void testParsePortOnlyFirstArgumentIsUsed() {
        assertEquals(8080, parsePort("8080", "9090"));
        assertEquals(DEFAULT_PORT, parsePort("--debug", "8080"));
    }

    @Test
    void testParsePortDefaultWithoutArguments() {
        assertEquals(42000, DEFAULT_PORT);
        assertEquals(DEFAULT_PORT, parsePort());
        assertEquals(DEFAULT_PORT, ControlpageBackendApplication.parsePort(null));
    }

    @Test
    void testParsePortDefaultForNonNumericArgument() {
        assertEquals(DEFAULT_PORT, parsePort(""));
        assertEquals(DEFAULT_PORT, parsePort("abc"));
        assertEquals(DEFAULT_PORT, parsePort("8080]"));
        assertEquals(DEFAULT_PORT, parsePort("-8080"));
        assertEquals(DEFAULT_PORT, parsePort(" 8080"));
        assertEquals(DEFAULT_PORT, parsePort("80a"));
        assertEquals(DEFAULT_PORT, parsePort("--server.port=8080"));
    }

    @Test
    void testParsePortDefaultForTooLongNumber() {
        // would overflow an int and is no valid port anyway
        assertEquals(DEFAULT_PORT, parsePort("99999999999"));
    }
}
