package eu.fischerserver.controlpage.controlpagebackend.model.entity;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.HorizontalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.VerticalAlignment;
import jakarta.persistence.*;
import lombok.*;

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
