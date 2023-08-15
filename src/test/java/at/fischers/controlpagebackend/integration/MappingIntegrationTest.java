package at.fischers.controlpagebackend.integration;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.dto.StyledText;
import at.fischers.controlpagebackend.dto.action.RestAction;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.entity.StyledTextEntity;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.enums.RestType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import at.fischers.controlpagebackend.service.ImageService;
import at.fischers.controlpagebackend.util.mapper.ViewMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MappingIntegrationTest {
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
        Field f1 = new Field(42, null, action, new StyledText("F1"), "description", -1, 1, 1);
        action.setField(f1);
        List<List<Field>> fields = List.of(
                List.of(f1)
        );
        FullView view = new FullView(42, "V1", group, fields);

        f1.setView(view);
        group.setViews(List.of(view));
        //
        ViewEntity viewEntity = ViewMapper.mapDTOToEntity(mockImageService, view);
        FieldEntity fieldEntity = viewEntity.getFields().get(0);

        /*
            Test
         */
        assertNotNull(fieldEntity.getView());
        assertNotNull(fieldEntity.getAction().getField());
        assertNotNull(viewEntity.getGroup().getViews());
        assertEquals(viewEntity.getGroup().getViews().size(), 1);
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
        FullView view = ViewMapper.mapEntityToFullDTO(viewEntity);
        Field field = view.getFields().get(0).get(0);

        /*
            Test
         */
        assertNotNull(field.getView());
        assertNotNull(field.getAction().getField());
        assertNotNull(view.getGroup().getViews());
        assertEquals(view.getGroup().getViews().size(), 1);
        assertNotNull(view.getGroup().getViews().get(0));
    }
}
