package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.entity.StyledTextEntity;
import at.fischers.controlpagebackend.enums.HorizontalAlignment;
import at.fischers.controlpagebackend.enums.VerticalAlignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link at.fischers.controlpagebackend.entity.StyledTextEntity} entity
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
