package at.fischers.controlpagebackend.unit.dto;

import at.fischers.controlpagebackend.dto.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    @Test
    void testEquals() {
        assertEquals(new Field(42, null, null, "F1", null, -1, -1), new Field(42, null, null, "F2", null, -1, -1));
        assertNotEquals(new Field(1, null, null, "F1", null, -1, -1), new Field(2, null, null, "F1", null, -1, -1));
    }
}
