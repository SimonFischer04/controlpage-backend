package at.fischers.controlpagebackend.unit.util.mapper;

import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.entity.ImageEntity;
import at.fischers.controlpagebackend.util.mapper.ImageMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageMapperTest {
    @Test
    void testMapEntityToDTO() {
        /*
            Test1: basic mapping test
         */
        {
            ImageEntity imageEntity = new ImageEntity(42, "img-name", "png", new byte[]{127, 42, 0});

            Image image = ImageMapper.mapEntityToDTO(imageEntity);
            assertNotNull(image);
            assertEquals(image.getId(), 42);
            assertEquals(image.getName(), "img-name");
            assertEquals(image.getType(), "png");
            assertEquals(image.getImageData()[1], 42);
        }
    }

    @Test
    void testMapDTOToEntity() {
        /*
            Test1: basic mapping test
         */
        {
            Image image = new Image(42, "img_photo", "jpg", new byte[]{1, 2, 3});

            ImageEntity imageEntity = ImageMapper.mapDTOToEntity(image);
            assertNotNull(imageEntity);
            assertEquals(imageEntity.getId(), 42);
            assertEquals(imageEntity.getName(), "img_photo");
            assertEquals(imageEntity.getType(), "jpg");
            assertEquals(imageEntity.getImageData()[2], 3);
        }
    }
}
