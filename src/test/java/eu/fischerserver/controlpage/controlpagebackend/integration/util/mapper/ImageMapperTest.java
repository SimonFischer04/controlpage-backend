package eu.fischerserver.controlpage.controlpagebackend.integration.util.mapper;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.ImageEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest()
public class ImageMapperTest {
    @Autowired
    private ConversionService conversionService;

    @Test
    void testMapEntityToDTO() {
        /*
            Test1: basic mapping test
         */
        {
            ImageEntity imageEntity = new ImageEntity(42, "img-name", "png", new byte[]{127, 42, 0});

            Image image = conversionService.convert(imageEntity, Image.class);
            assertNotNull(image);
            assertEquals(42, image.id());
            assertEquals("img-name", image.name());
            assertEquals("png", image.type());
            assertEquals(127, image.imageData()[0]);
            assertEquals(42, image.imageData()[1]);
            assertEquals(0, image.imageData()[2]);
        }
    }

    @Test
    void testMapDTOToEntity() {
        /*
            Test1: basic mapping test
         */
        {
            Image image = new Image(42, "img_photo", "jpg", new byte[]{1, 2, 3});

            ImageEntity imageEntity = conversionService.convert(image, ImageEntity.class);
            assertNotNull(imageEntity);
            assertEquals(42, imageEntity.getId());
            assertEquals("img_photo", imageEntity.getName());
            assertEquals("jpg", imageEntity.getType());
            assertEquals(3, imageEntity.getImageData()[2]);
        }
    }
}
