package eu.fischerserver.controlpage.controlpagebackend.model.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Image(
        int id,

        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Type is mandatory")
        String type,

        byte[] imageData
) {
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
