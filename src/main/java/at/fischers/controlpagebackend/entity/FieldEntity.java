package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.entity.action.ActionEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "field")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    private ViewEntity view;

    @OneToOne
    private ActionEntity action;

    private String title;
    private String background;

    private int rowspan;
    private int colspan;

    private int xPos;
    private int yPos;
}
