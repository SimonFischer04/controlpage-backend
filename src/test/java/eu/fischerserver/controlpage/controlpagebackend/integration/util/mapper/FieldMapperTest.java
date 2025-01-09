package eu.fischerserver.controlpage.controlpagebackend.integration.util.mapper;

import eu.fischerserver.controlpage.controlpagebackend.integration.config.ControlpageIntegrationTestBase;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyDomainUtils;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyEntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import static org.junit.jupiter.api.Assertions.*;

public class FieldMapperTest extends ControlpageIntegrationTestBase {
    @Autowired
    ConversionService conversionService;

    @Test
    void testMapEntityToDTO() {
        /*
            Test 1: test with everything(except view - must be set by viewMapper) set
         */
        {
            FieldEntity fieldEntity = DummyEntityUtils.getDummyFieldEntity();

            Field field = conversionService.convert(fieldEntity, Field.class);
            assertNotNull(field);
            assertEquals(fieldEntity.getId(), field.id());
            assertEquals(fieldEntity.getTitle().getText(), field.title().text());
            assertNotNull(field.action());
            assertNotNull(field.background());
            assertEquals(fieldEntity.getRowspan(), field.rowspan());
            assertEquals(fieldEntity.getColspan(), field.colspan());
        }

        /*
            Test 2: no exception thrown when some values are null
         */
        {
            assertDoesNotThrow(() -> {
                conversionService.convert(new FieldEntity(0, null, null, null, "", null, 1, 2, 3, 4), Field.class);
            });
        }
    }

    @Test
    void testMapDTOToEntity() {
        /*
            Test 1: test with everything(except view - must be set by viewMapper) set
         */
        {
            Field field = DummyDomainUtils.getDummyField();

            FieldEntity fieldEntity = conversionService.convert(field, FieldEntity.class);

            assertNotNull(fieldEntity);
            assertEquals(field.id(), fieldEntity.getId());
            assertNotNull(fieldEntity.getAction());
            assertEquals(field.title().text(), fieldEntity.getTitle().getText());
            assertNotNull(fieldEntity.getBackground());
            assertEquals(field.rowspan(), fieldEntity.getRowspan());
            assertEquals(field.colspan(), fieldEntity.getColspan());
        }

        /*
            Test 2: no exception thrown when some values are null
        */
        {
            assertDoesNotThrow(() -> {
                conversionService.convert(new Field(42, null, null, null, "", new Image(42, "name", "img/png", new byte[]{127, 42}), 1, 2), FieldEntity.class);
            });
        }
    }
}
