package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Image;
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
    @ToString.Exclude
    private ViewEntity view;

    @OneToOne(orphanRemoval = true)
    private ActionEntity action;

    private String title;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ImageEntity background;

    private int rowspan;
    private int colspan;

    private int xPos;
    private int yPos;

    public FieldEntity(Field field) {
        id = field.getId();
        if (field.getAction() != null) {
            action = new ActionEntity(field.getAction());
            action.setField(this);
        }
        title = field.getTitle();
        if (field.getBackground() != null) {
            background = new ImageEntity(field.getBackground());
        }
        rowspan = field.getRowspan();
        colspan = field.getColspan();
    }
}
