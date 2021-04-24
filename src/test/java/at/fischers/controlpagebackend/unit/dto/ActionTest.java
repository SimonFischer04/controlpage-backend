package at.fischers.controlpagebackend.unit.dto;

import at.fischers.controlpagebackend.dto.action.RestAction;
import at.fischers.controlpagebackend.dto.action.ViewAction;
import at.fischers.controlpagebackend.enums.RestType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import at.fischers.controlpagebackend.enums.ViewActionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {
    @Test
    void testEquals() {
        assertEquals(new RestAction(42, null, RunPolicy.ASYNC, RestType.GET, "", ""), new RestAction(42, null, RunPolicy.ASYNC, RestType.GET, "", ""));
        assertNotEquals(new RestAction(1, null, RunPolicy.ASYNC, RestType.GET, "", ""), new RestAction(2, null, RunPolicy.ASYNC, RestType.GET, "", ""));

        assertEquals(new ViewAction(42, null, RunPolicy.ASYNC, ViewActionType.SWITCH, 0), new ViewAction(42, null, RunPolicy.ASYNC, ViewActionType.SWITCH, 0));
        assertNotEquals(new ViewAction(1, null, RunPolicy.ASYNC, ViewActionType.SWITCH, 0), new ViewAction(2, null, RunPolicy.ASYNC, ViewActionType.SWITCH, 0));
    }
}
