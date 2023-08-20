package eu.fischerserver.controlpage.controlpagebackend.unit.dto;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.StyledText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    @Test
    void testEquals() {
        assertEquals(
                new Field(42, null, null, new StyledText("F1"), "", null, 1, 1),
                new Field(42, null, null, new StyledText("F2"), "", null, 1, 1)
        );
        assertNotEquals(
                new Field(1, null, null, new StyledText("F1"), "", null, 1, 1),
                new Field(2, null, null, new StyledText("F1"), "", null, 1, 1)
        );
    }
}
