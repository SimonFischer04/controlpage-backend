package at.fischers.controlpagebackend.model.entity;

import at.fischers.controlpagebackend.model.domain.text.StyledText;
import at.fischers.controlpagebackend.model.domain.text.HorizontalAlignment;
import at.fischers.controlpagebackend.model.domain.text.VerticalAlignment;
import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "styled_text_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StyledTextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated
    @Column(name = "horizontal_alignment", nullable = false)
    private HorizontalAlignment horizontalAlignment;

    @Enumerated
    @Column(name = "vertical_alignment", nullable = false)
    private VerticalAlignment verticalAlignment;

    @Column(name = "color", length = 7)
    private String color;

    public StyledTextEntity(String text){
        this(0, text, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, "#FFFFFF");
    }

    public StyledTextEntity(StyledTextEntity styledTextEntity) {
        id = styledTextEntity.id;
        text = styledTextEntity.text;
        horizontalAlignment = styledTextEntity.horizontalAlignment;
        verticalAlignment = styledTextEntity.verticalAlignment;
        color = styledTextEntity.color;
    }

    public StyledTextEntity(StyledText styledText) {
        id = styledText.getId();
        text = styledText.getText();
        horizontalAlignment = styledText.getHorizontalAlignment();
        verticalAlignment = styledText.getVerticalAlignment();
        color = styledText.getColor();
    }

    /*
        Because they are stored in a database two StyledTextEntity with the same id are considered equals
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StyledTextEntity styledText = (StyledTextEntity) o;
        return id == styledText.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
