package eu.fischerserver.controlpage.controlpagebackend.unit.entity;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GroupEntityTest {
    @Test
    void testEquals() {
        assertEquals(new GroupEntity(42, null, null, "G1", null), new GroupEntity(42, null, null, "G2", null));
        assertNotEquals(new GroupEntity(1, null, null, "G1", null), new GroupEntity(2, null, null, "G1", null));
    }
}
