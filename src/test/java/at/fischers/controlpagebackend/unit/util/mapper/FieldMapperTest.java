package at.fischers.controlpagebackend.unit.util.mapper;

import at.fischers.controlpagebackend.model.domain.Field;
import at.fischers.controlpagebackend.model.domain.Image;
import at.fischers.controlpagebackend.model.domain.text.StyledText;
import at.fischers.controlpagebackend.model.domain.action.RestAction;
import at.fischers.controlpagebackend.model.entity.FieldEntity;
import at.fischers.controlpagebackend.model.entity.ImageEntity;
import at.fischers.controlpagebackend.model.entity.StyledTextEntity;
import at.fischers.controlpagebackend.model.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.model.global.action.RestType;
import at.fischers.controlpagebackend.model.global.action.RunPolicy;
import at.fischers.controlpagebackend.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FieldMapperTest {
    @Autowired
    ConversionService conversionService;

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

            Field field = conversionService.convert(fieldEntity, Field.class);
            assertNotNull(field);
            assertEquals(42, field.getId());
            assertEquals("A Title", field.getTitle().getText());
            assertNotNull(field.getAction());
            assertNotNull(field.getBackground());
            assertEquals(1, field.getRowspan());
            assertEquals(2, field.getColspan());
        }

        // TODO: still relevant with new mapper?
        /*
            Test 2: no exception thrown when some values are null
         */
        {
            assertDoesNotThrow(() -> {
                conversionService.convert(new FieldEntity(0, null, null, new StyledTextEntity("Title"), "", null, 1, 2, 3, 4), Field.class);
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
            Field field = new Field(42, null, restAction, new StyledText("great Title"), "", new Image(42, "name", "img/png", new byte[]{127, 42}), 1, 2);

            FieldEntity fieldEntity = conversionService.convert(field, FieldEntity.class);

            assertNotNull(fieldEntity);
            assertEquals(42, fieldEntity.getId(), 42);
            assertNotNull(fieldEntity.getAction());
            assertEquals("great Title", fieldEntity.getTitle().getText());
            assertNotNull(fieldEntity.getBackground());
            assertEquals(1, fieldEntity.getRowspan());
            assertEquals(2, fieldEntity.getColspan());
        }

        // TODO: still relevant with new mapper?
        /*
            Test 2: no exception thrown when some values are null
        */
        {
            assertDoesNotThrow(() -> {
                conversionService.convert(new Field(42, null, null, new StyledText("great Title"), "", new Image(42, "name", "img/png", new byte[]{127, 42}), 1, 2), FieldEntity.class);
            });
        }
    }
}
