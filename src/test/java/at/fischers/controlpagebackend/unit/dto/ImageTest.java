package at.fischers.controlpagebackend.unit.dto;

import at.fischers.controlpagebackend.model.domain.Image;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImageTest {
    @Test
    void testEquals() {
        assertEquals(new Image(42, "I1", "", null), new Image(42, "I2", "", null));
        assertNotEquals(new Image(1, "I1", "", null), new Image(2, "I1", "", null));
    }
}
