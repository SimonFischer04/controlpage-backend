package at.fischers.controlpagebackend.unit.entity;

import at.fischers.controlpagebackend.model.entity.ImageEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ImageEntityTest {
    @Test
    void testEquals() {
        assertEquals(new ImageEntity(42, "I1", "", null), new ImageEntity(42, "I2", "", null));
        assertNotEquals(new ImageEntity(1, "I1", "", null), new ImageEntity(2, "I1", "", null));
    }
}
