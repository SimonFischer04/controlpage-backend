package at.fischers.controlpagebackend.unit.entity;

import at.fischers.controlpagebackend.entity.FieldEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FieldEntityTest {
    @Test
    void testEquals() {
        assertEquals(new FieldEntity(42, null, null, "F1", null, -1, -1, 0, 0), new FieldEntity(42, null, null, "F2", null, -1, -1, 0, 0));
        assertNotEquals(new FieldEntity(1, null, null, "F1", null, -1, -1, 0, 0), new FieldEntity(2, null, null, "F1", null, -1, -1, 0, 0));
    }
}
