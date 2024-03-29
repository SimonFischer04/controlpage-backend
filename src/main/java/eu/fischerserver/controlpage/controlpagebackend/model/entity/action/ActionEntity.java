package eu.fischerserver.controlpage.controlpagebackend.model.entity.action;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "action")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class ActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @ToString.Exclude
    private FieldEntity field;

    private RunPolicy runPolicy;

    /*
        Because they are stored in a database two Actions with the same id are considered equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionEntity action = (ActionEntity) o;
        return id == action.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
