package eu.fischerserver.controlpage.controlpagebackend.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ImageEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name, type;

    // up to 16 MiB; without a database specific column type (bytea in PostgreSQL)
    @Column(name = "image_data", length = 16 * 1024 * 1024)
    private byte[] imageData;

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
