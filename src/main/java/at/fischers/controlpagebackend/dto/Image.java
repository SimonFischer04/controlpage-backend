package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.entity.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {
    private int id;
    private String name, type;
    private byte[] imageData;

    public Image(ImageEntity entity) {
        id = entity.getId();
        name = entity.getName();
        type = entity.getType();
        imageData = entity.getImageData();
    }
}
