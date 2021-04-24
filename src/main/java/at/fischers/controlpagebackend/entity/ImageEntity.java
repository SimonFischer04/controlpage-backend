package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.util.mapper.ImageMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name, type;

    @Lob
    private byte[] imageData;

    public ImageEntity(ImageEntity imageEntity) {
        id = imageEntity.getId();
        name = imageEntity.getName();
        type = imageEntity.getType();
        imageData = imageEntity.getImageData();
    }

    public ImageEntity(Image image) {
        this(ImageMapper.mapDTOToEntity(image));
    }

    /*
        Because they are stored in a database two Images with the same id are considered equals
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity image = (ImageEntity) o;
        return id == image.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
