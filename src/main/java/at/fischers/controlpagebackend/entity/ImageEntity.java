package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name, type;

    @Lob
    private byte[] imageData;

    public ImageEntity(Image image) {
        id = image.getId();
        name = image.getName();
        type = image.getType();
        imageData = image.getImageData();
    }
}
