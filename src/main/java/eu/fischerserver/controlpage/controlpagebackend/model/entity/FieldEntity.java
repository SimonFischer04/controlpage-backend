package eu.fischerserver.controlpage.controlpagebackend.model.entity;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ActionEntity;
import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "field")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private ViewEntity view;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ActionEntity action;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "title_id")
    private StyledTextEntity title;

    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ImageEntity background;

    private int rowspan;
    private int colspan;

    private int xPos;
    private int yPos;

    /*
        Because they are stored in a database two Fields with the same id are considered equals
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldEntity field = (FieldEntity) o;
        return id == field.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
