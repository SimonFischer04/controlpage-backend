package at.fischers.controlpagebackend.entity.action;

import at.fischers.controlpagebackend.entity.Field;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @ToString.Exclude
    @JsonBackReference
    private Field field;

    @Column(nullable = false)
    private ActionType actionType;

    @Column(nullable = false)
    private String actionValue;
}
