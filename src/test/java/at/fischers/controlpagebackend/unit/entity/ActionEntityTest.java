package at.fischers.controlpagebackend.unit.entity;

import at.fischers.controlpagebackend.model.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.model.entity.action.ViewActionEntity;
import at.fischers.controlpagebackend.model.global.action.RestType;
import at.fischers.controlpagebackend.model.global.action.RunPolicy;
import at.fischers.controlpagebackend.model.global.action.ViewActionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ActionEntityTest {
    @Test
    void testEquals() {
        assertEquals(new RestActionEntity(42, null, RunPolicy.ASYNC, RestType.GET, "", ""), new RestActionEntity(42, null, RunPolicy.ASYNC, RestType.GET, "", ""));
        assertNotEquals(new RestActionEntity(1, null, RunPolicy.ASYNC, RestType.GET, "", ""), new RestActionEntity(2, null, RunPolicy.ASYNC, RestType.GET, "", ""));

        assertEquals(new ViewActionEntity(42, null, RunPolicy.ASYNC, ViewActionType.SWITCH_TO, 0), new ViewActionEntity(42, null, RunPolicy.ASYNC, ViewActionType.SWITCH_TO, 0));
        assertNotEquals(new ViewActionEntity(1, null, RunPolicy.ASYNC, ViewActionType.SWITCH_TO, 0), new ViewActionEntity(2, null, RunPolicy.ASYNC, ViewActionType.SWITCH_TO, 0));
    }
}
