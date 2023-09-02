package eu.fischerserver.controlpage.controlpagebackend.integration;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.StyledTextEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.RestActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyDomainUtils;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyEntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MappingIntegrationTest {
    @Autowired
    ConversionService conversionService;

    /**
     * Test: test combination of mapper (back references are set)
     * f.e. ViewMapper(mapDTOToEntity) has to set fieldEntity.viewEntity
     */
    @Test
    void testMappingIntegrationDTOToEntity() {
        /*
            Init Test Data
         */
        FullView view = DummyDomainUtils.getDummyFullView();

        //
        ViewEntity viewEntity = conversionService.convert(view, ViewEntity.class);
        assertNotNull(viewEntity);
//        FieldEntity fieldEntity = viewEntity.getFields().get(0);

        /*
            Test
         */
        // TODO: necessary?
//        assertNotNull(fieldEntity.getView());
//        assertNotNull(fieldEntity.getAction().getField());
        assertNotNull(viewEntity.getGroup().getViews());
        assertEquals(1, viewEntity.getGroup().getViews().size());
        assertNotNull(viewEntity.getGroup().getViews().get(0));
    }

    /**
     * Test: test combination of mapper (back references are set)
     * f.e. ViewMapper(mapEntityToDTO) has to set field.view
     */
    @Test
    void testMappingIntegrationEntityToDTO() {
        /*
            Init Test Data
         */
        ViewEntity viewEntity = DummyEntityUtils.getDummyViewEntity();

        //
        FullView view = conversionService.convert(viewEntity, FullView.class);
        assertNotNull(view);
        Field field = view.getFields().get(0).get(0);

        /*
            Test
         */
        // TODO: necessary?
//        assertNotNull(field.getView());
//        assertNotNull(field.getAction().getField());
        assertNotNull(view.getGroup().views());
        assertEquals(1, view.getGroup().views().size());
        assertNotNull(view.getGroup().views().get(0));
    }
}
