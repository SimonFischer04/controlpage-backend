package at.fischers.controlpagebackend.entity.action;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.enums.RunPolicy;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "action")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @ToString.Exclude
    private FieldEntity field;

    private RunPolicy runPolicy;

    public ActionEntity(Action action) {
        // TODO: should be unused if mapper class finished -> make this class abstract
        id = action.getId();
        runPolicy = action.getRunPolicy();
    }
}
