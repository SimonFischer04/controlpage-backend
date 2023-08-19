package at.fischers.controlpagebackend.unit.dto;

import at.fischers.controlpagebackend.model.domain.action.RestAction;
import at.fischers.controlpagebackend.model.domain.action.ViewAction;
import at.fischers.controlpagebackend.model.global.action.RestType;
import at.fischers.controlpagebackend.model.global.action.RunPolicy;
import at.fischers.controlpagebackend.model.global.action.ViewActionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {
    @Test
    void testEquals() {
        assertEquals(new RestAction(42, null, RunPolicy.ASYNC, RestType.GET, "", ""), new RestAction(42, null, RunPolicy.ASYNC, RestType.GET, "", ""));
        assertNotEquals(new RestAction(1, null, RunPolicy.ASYNC, RestType.GET, "", ""), new RestAction(2, null, RunPolicy.ASYNC, RestType.GET, "", ""));

        assertEquals(new ViewAction(42, null, RunPolicy.ASYNC, ViewActionType.SWITCH_TO, 0), new ViewAction(42, null, RunPolicy.ASYNC, ViewActionType.SWITCH_TO, 0));
        assertNotEquals(new ViewAction(1, null, RunPolicy.ASYNC, ViewActionType.SWITCH_TO, 0), new ViewAction(2, null, RunPolicy.ASYNC, ViewActionType.SWITCH_TO, 0));
    }
}
