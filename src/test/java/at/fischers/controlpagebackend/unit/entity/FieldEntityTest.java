package at.fischers.controlpagebackend.unit.entity;

import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.StyledTextEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FieldEntityTest {
    @Test
    void testEquals() {
        assertEquals(
                new FieldEntity(42, null, null, new StyledTextEntity("F1"),"", null, 1, 1, 0, 0),
                new FieldEntity(42, null, null, new StyledTextEntity("F2"),"", null, 1, 1, 0, 0)
        );
        assertNotEquals(
                new FieldEntity(1, null, null, new StyledTextEntity("F1"),"", null, 1, 1, 0, 0),
                new FieldEntity(2, null, null, new StyledTextEntity("F1"),"", null, 1, 1, 0, 0)
        );
    }
}
