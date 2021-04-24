package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.action.ActionEntity;
import at.fischers.controlpagebackend.util.mapper.FieldMapper;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "field")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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

    public FieldEntity(FieldEntity fieldEntity) {
        id = fieldEntity.getId();
        view = fieldEntity.getView();
        action = fieldEntity.getAction();
        title = fieldEntity.getTitle();
        background = fieldEntity.getBackground();
        rowspan = fieldEntity.getRowspan();
        colspan = fieldEntity.getColspan();
        xPos = fieldEntity.getXPos();
        yPos = fieldEntity.getYPos();
    }

    public FieldEntity(Field field) {
        this(FieldMapper.mapDTOToEntity(field));
    }

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
