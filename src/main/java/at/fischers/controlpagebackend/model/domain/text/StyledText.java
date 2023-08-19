package at.fischers.controlpagebackend.model.domain.text;

import at.fischers.controlpagebackend.model.entity.StyledTextEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link StyledTextEntity} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StyledText implements Serializable {
    private long id;
    private String text;
    private HorizontalAlignment horizontalAlignment;
    private VerticalAlignment verticalAlignment;
    private String color;

    public StyledText(String text) {
        this(0, text, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, "#FFFFFF");
    }

    public StyledText(StyledText styledText) {
        id = styledText.id;
        text = styledText.text;
        horizontalAlignment = styledText.horizontalAlignment;
        verticalAlignment = styledText.verticalAlignment;
        color = styledText.color;
    }

    public StyledText(StyledTextEntity styledTextEntity) {
        id = styledTextEntity.getId();
        text = styledTextEntity.getText();
        horizontalAlignment = styledTextEntity.getHorizontalAlignment();
        verticalAlignment = styledTextEntity.getVerticalAlignment();
        color = styledTextEntity.getColor();
    }

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
