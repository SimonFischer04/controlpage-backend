package at.fischers.controlpagebackend.unit.util.mapper;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.dto.StyledText;
import at.fischers.controlpagebackend.dto.action.RestAction;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.ImageEntity;
import at.fischers.controlpagebackend.entity.StyledTextEntity;
import at.fischers.controlpagebackend.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.enums.RestType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import at.fischers.controlpagebackend.service.ImageService;
import at.fischers.controlpagebackend.util.mapper.FieldMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldMapperTest {
    // TODO: make this better
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

    @Test
    void testMapEntityToDTO() {
        /*
            Test 1: test with everything(except view - must be set by viewMapper) set
         */
        {
            RestActionEntity restActionEntity = new RestActionEntity(1337, null, RunPolicy.ASYNC, RestType.GET, "127.0.0.1:4242", "{}");
            FieldEntity fieldEntity = new FieldEntity(42, null, restActionEntity, new StyledTextEntity("A Title"), "", new ImageEntity(42, "IMG", "png", new byte[]{}), 1, 2, 3, 4);

            Field field = FieldMapper.mapEntityToDTO(fieldEntity);
            assertNotNull(field);
            assertEquals(field.getId(), 42);
            assertEquals(field.getTitle().getText(), "A Title");
            assertNotNull(field.getAction());
            assertNotEquals(field.getBackgroundId(), 0);
            assertEquals(field.getRowspan(), 1);
            assertEquals(field.getColspan(), 2);
        }

        /*
            Test 2: no exception thrown when some values are null
         */
        {
            assertDoesNotThrow(() -> {
                FieldMapper.mapEntityToDTO(new FieldEntity(0, null, null, new StyledTextEntity("Title"), "", null, 1, 2, 3, 4));
            });
        }
    }

    @Test
    void testMapDTOToEntity() {
        /*
            Test 1: test with everything(except view - must be set by viewMapper) set
         */
        {
            RestAction restAction = new RestAction(1337, null, RunPolicy.SYNC, RestType.POST, "127.0.0.1:4242", "{}");
            Field field = new Field(42, null, restAction, new StyledText("great Title"),"", -1, 1, 2);

            FieldEntity fieldEntity = FieldMapper.mapDTOToEntity(mockImageService, field);

            assertNotNull(fieldEntity);
            assertEquals(fieldEntity.getId(), 42);
            assertNotNull(fieldEntity.getAction());
            assertEquals(fieldEntity.getTitle().getText(), "great Title");
            assertNotNull(fieldEntity.getBackground());
            assertEquals(fieldEntity.getRowspan(), 1);
            assertEquals(fieldEntity.getColspan(), 2);
        }

        /*
            Test 2: no exception thrown when some values are null
        */
        {
            assertDoesNotThrow(() -> {
                FieldMapper.mapDTOToEntity(mockImageService, new Field(42, null, null, new StyledText("great Title"), "", -1, 1, 2));
            });
        }
    }
}
