package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.entity.ImageEntity;
import at.fischers.controlpagebackend.util.mapper.ImageMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Image {
    private int id;
    private String name, type;
    private byte[] imageData;

    public Image(Image image) {
        id = image.getId();
        name = image.getName();
        type = image.getType();
        imageData = image.getImageData();
    }

    public Image(ImageEntity entity) {
        this(ImageMapper.mapEntityToDTO(entity));
    }
}
