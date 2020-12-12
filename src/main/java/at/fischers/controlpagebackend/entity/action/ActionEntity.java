package at.fischers.controlpagebackend.entity.action;

import at.fischers.controlpagebackend.entity.FieldEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "action")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private FieldEntity field;

    @Column(nullable = false)
    private ActionType actionType;

    @Column(nullable = false)
    private String actionValue;
}
