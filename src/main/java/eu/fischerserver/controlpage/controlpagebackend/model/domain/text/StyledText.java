package eu.fischerserver.controlpage.controlpagebackend.model.domain.text;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.StyledTextEntity;
import lombok.Builder;

import java.util.Objects;

/**
 * A DTO for the {@link StyledTextEntity} entity
 */
@Builder
public record StyledText(
        long id,
        String text,
        HorizontalAlignment horizontalAlignment,
        VerticalAlignment verticalAlignment,
        String color
) {
    /*
        Because they are stored in a database two StyledTextEntity with the same id are considered equals
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StyledText styledText = (StyledText) o;
        return id == styledText.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
