package eu.fischerserver.controlpage.controlpagebackend.unit.dto;

import eu.fischerserver.controlpage.controlpagebackend.util.DummyDomainUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FieldTest {
    @Test
    void testEquals() {
        assertEquals(
                DummyDomainUtils.getDummyField(42),
                DummyDomainUtils.getDummyField(42)
        );
        assertNotEquals(
                DummyDomainUtils.getDummyField(1),
                DummyDomainUtils.getDummyField(2)
        );
    }
}
