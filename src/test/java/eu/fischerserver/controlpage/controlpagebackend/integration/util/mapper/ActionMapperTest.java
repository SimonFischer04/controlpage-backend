package eu.fischerserver.controlpage.controlpagebackend.integration.util.mapper;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.Action;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.RestAction;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.ViewAction;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.RestActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ViewActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RestType;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.ViewActionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ActionMapperTest {
    @Autowired
    ConversionService conversionService;

    @Test
    void testMapEntityToDTO() {
        /*
            Test 1: test mapping RestActionEntity
         */
        {
            RestActionEntity restActionEntity = new RestActionEntity(42, null, RunPolicy.ASYNC, RestType.GET, "127.0.0.1:4242", "{}");
            Action action = conversionService.convert(restActionEntity, Action.class);

            assertNotNull(action);
            assertTrue(action instanceof RestAction);
            RestAction restAction = (RestAction) action;

            assertEquals(42, restAction.getId());
            assertEquals(RunPolicy.ASYNC, restAction.getRunPolicy());
            assertEquals(RestType.GET, restAction.getRestType());
            assertEquals("127.0.0.1:4242", restAction.getUrl());
            assertEquals("{}", restAction.getBody());
        }

        /*
            Test 2: test mapping ViewActionEntity
         */
        {
            ViewActionEntity viewActionEntity = new ViewActionEntity(42, null, RunPolicy.ASYNC, ViewActionType.CLOSE, 1337);
            Action action2 = conversionService.convert(viewActionEntity, Action.class);

            assertNotNull(action2);
            assertTrue(action2 instanceof ViewAction);
            ViewAction viewAction = (ViewAction) action2;

            assertEquals(42, viewAction.getId());
            assertEquals(RunPolicy.ASYNC, viewAction.getRunPolicy());
            assertEquals(ViewActionType.CLOSE, viewAction.getViewActionType());
            assertEquals(1337, viewAction.getViewId());
        }

        // TODO: DAAutomationAction
    }

    @Test
    void testMapDTOToEntity() {
         /*
            Test 1: test mapping RestAction
         */
        {
            RestAction restAction = new RestAction(42, null, RunPolicy.ASYNC, RestType.GET, "127.0.0.1:42", "{}");
            ActionEntity actionEntity = conversionService.convert(restAction, ActionEntity.class);

            assertNotNull(actionEntity);
            assertTrue(actionEntity instanceof RestActionEntity);
            RestActionEntity restActionEntity = (RestActionEntity) actionEntity;

            assertEquals(42, restActionEntity.getId());
            assertEquals(RunPolicy.ASYNC, restActionEntity.getRunPolicy());
            assertEquals(RestType.GET, restActionEntity.getRestType());
            assertEquals("127.0.0.1:42", restActionEntity.getUrl());
            assertEquals("{}", restActionEntity.getBody());
        }

        /*
            Test 2: test mapping ViewAction
         */
        {
            ViewAction viewAction = new ViewAction(42, null, RunPolicy.ASYNC, ViewActionType.CLOSE, 1337);
            ActionEntity actionEntity2 = conversionService.convert(viewAction, ActionEntity.class);

            assertNotNull(actionEntity2);
            assertTrue(actionEntity2 instanceof ViewActionEntity);
            ViewActionEntity viewActionEntity = (ViewActionEntity) actionEntity2;

            assertEquals(42, viewActionEntity.getId());
            assertEquals(RunPolicy.ASYNC, viewActionEntity.getRunPolicy());
            assertEquals(ViewActionType.CLOSE, viewActionEntity.getViewActionType());
            assertEquals(1337, viewActionEntity.getViewId());
        }

        // TODO: DAAutomationAction
    }
}
