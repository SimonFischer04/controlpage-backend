package at.fischers.controlpagebackend.unit.util.mapper;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.dto.action.RestAction;
import at.fischers.controlpagebackend.dto.action.ViewAction;
import at.fischers.controlpagebackend.entity.action.ActionEntity;
import at.fischers.controlpagebackend.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.entity.action.ViewActionEntity;
import at.fischers.controlpagebackend.enums.ActionType;
import at.fischers.controlpagebackend.enums.RestType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import at.fischers.controlpagebackend.enums.ViewActionType;
import at.fischers.controlpagebackend.util.mapper.ActionMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionMapperTest {
    @Test
    void testMapEntityToDTO() {
        /*
            Test 1: test mapping RestActionEntity
         */
        {
            RestActionEntity restActionEntity = new RestActionEntity(42, null, RunPolicy.ASYNC, RestType.GET, "127.0.0.1:4242", "{}");
            Action action = ActionMapper.mapEntityToDTO(restActionEntity);

            assertNotNull(action);
            assertTrue(action instanceof RestAction);
            RestAction restAction = (RestAction) action;

            assertEquals(restAction.getId(), 42);
            assertEquals(restAction.getActionType(), ActionType.REST);
            assertEquals(restAction.getRunPolicy(), RunPolicy.ASYNC);
            assertEquals(restAction.getRestType(), RestType.GET);
            assertEquals(restAction.getUrl(), "127.0.0.1:4242");
            assertEquals(restAction.getBody(), "{}");
        }

        /*
            Test 2: test mapping ViewActionEntity
         */
        {
            ViewActionEntity viewActionEntity = new ViewActionEntity(42, null, RunPolicy.ASYNC, ViewActionType.CLOSE, 1337);
            Action action2 = ActionMapper.mapEntityToDTO(viewActionEntity);

            assertNotNull(action2);
            assertTrue(action2 instanceof ViewAction);
            ViewAction viewAction = (ViewAction) action2;

            assertEquals(viewAction.getId(), 42);
            assertEquals(viewAction.getActionType(), ActionType.VIEW);
            assertEquals(viewAction.getRunPolicy(), RunPolicy.ASYNC);
            assertEquals(viewAction.getViewActionType(), ViewActionType.CLOSE);
            assertEquals(viewAction.getViewId(), 1337);
        }
    }

    @Test
    void testMapDTOToEntity() {
         /*
            Test 1: test mapping RestAction
         */
        {
            RestAction restAction = new RestAction(42, null, RunPolicy.ASYNC, RestType.GET, "127.0.0.1:42", "{}");
            ActionEntity actionEntity = ActionMapper.mapDTOToEntity(restAction);

            assertNotNull(actionEntity);
            assertTrue(actionEntity instanceof RestActionEntity);
            RestActionEntity restActionEntity = (RestActionEntity) actionEntity;

            assertEquals(restActionEntity.getId(), 42);
            assertEquals(restActionEntity.getRunPolicy(), RunPolicy.ASYNC);
            assertEquals(restActionEntity.getType(), RestType.GET);
            assertEquals(restActionEntity.getUrl(), "127.0.0.1:42");
            assertEquals(restActionEntity.getBody(), "{}");
        }

        /*
            Test 2: test mapping ViewAction
         */
        {
            ViewAction viewAction = new ViewAction(42, null, RunPolicy.ASYNC, ViewActionType.CLOSE, 1337);
            ActionEntity actionEntity2 = ActionMapper.mapDTOToEntity(viewAction);

            assertNotNull(actionEntity2);
            assertTrue(actionEntity2 instanceof ViewActionEntity);
            ViewActionEntity viewActionEntity = (ViewActionEntity) actionEntity2;

            assertEquals(viewActionEntity.getId(), 42);
            assertEquals(viewActionEntity.getRunPolicy(), RunPolicy.ASYNC);
            assertEquals(viewActionEntity.getType(), ViewActionType.CLOSE);
            assertEquals(viewActionEntity.getViewId(), 1337);
        }
    }
}
