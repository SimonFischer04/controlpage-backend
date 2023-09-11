package eu.fischerserver.controlpage.controlpagebackend.unit.entity;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.StyledTextEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyEntityUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FieldEntityTest {
    @Test
    void testEquals() {
        assertEquals(
                DummyEntityUtils.getDummyFieldEntity(42),
                DummyEntityUtils.getDummyFieldEntity(42)
        );
        assertNotEquals(
                DummyEntityUtils.getDummyFieldEntity(1),
                DummyEntityUtils.getDummyFieldEntity(2)
        );
    }
}
