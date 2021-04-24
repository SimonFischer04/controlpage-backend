package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.entity.ImageEntity;
import at.fischers.controlpagebackend.util.mapper.ImageMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    /*
        Because they are stored in a database two Images with the same id are considered equals
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
