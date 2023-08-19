package at.fischers.controlpagebackend.unit.dto;

import at.fischers.controlpagebackend.model.domain.Group;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {
    @Test
    void testEquals() {
        assertEquals(new Group(42, null, null, "G1", null), new Group(42, null, null, "G2", null));
        assertNotEquals(new Group(1, null, null, "G1", null), new Group(2, null, null, "G1", null));
    }
}
