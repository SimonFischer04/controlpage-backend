package eu.fischerserver.controlpage.controlpagebackend.integration;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.StyledText;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.RestAction;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.StyledTextEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.RestActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.service.ImageService;
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

    final ImageService mockImageService = new ImageService() {
        @Override
        public Image findById(int id) {
            return null;
        }

        @Override
        public Image save(Image image) {
            return null;
        }
    };

    /**
     * Test: test combination of mapper (back references are set)
     * f.e. ViewMapper(mapDTOToEntity) has to set fieldEntity.viewEntity
     */
    @Test
    void testMappingIntegrationDTOToEntity() {
        /*
            Init Test Data
         */
        Group group = new Group();
        RestAction action = new RestAction();
        Field f1 = new Field(42, null, action, new StyledText("F1"), "description", null, 1, 1);
        action.setField(f1);
        List<List<Field>> fields = List.of(
                List.of(f1)
        );
        FullView view = new FullView(42, "V1", group, fields);

        f1.setView(view);
        group.setViews(List.of(view));
        //
        ViewEntity viewEntity = conversionService.convert(view, ViewEntity.class);
        assertNotNull(viewEntity);
        FieldEntity fieldEntity = viewEntity.getFields().get(0);

        /*
            Test
         */
        assertNotNull(fieldEntity.getView());
        assertNotNull(fieldEntity.getAction().getField());
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
        GroupEntity group = new GroupEntity();
        RestActionEntity restActionEntity = new RestActionEntity();
        FieldEntity f1 = new FieldEntity(42, null, restActionEntity, new StyledTextEntity("F1"), "description", null, 1, 1, 0, 0);
        restActionEntity.setField(f1);
        List<FieldEntity> fields = List.of(f1);
        ViewEntity viewEntity = new ViewEntity(42, "V1", group, fields);

        f1.setView(viewEntity);
        group.setViews(List.of(viewEntity));
        //
        FullView view = conversionService.convert(viewEntity, FullView.class);
        assertNotNull(view);
        Field field = view.getFields().get(0).get(0);

        /*
            Test
         */
        assertNotNull(field.getView());
        assertNotNull(field.getAction().getField());
        assertNotNull(view.getGroup().getViews());
        assertEquals(1, view.getGroup().getViews().size());
        assertNotNull(view.getGroup().getViews().get(0));
    }
}
