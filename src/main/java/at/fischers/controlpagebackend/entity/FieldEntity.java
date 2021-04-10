package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.action.ActionEntity;
import at.fischers.controlpagebackend.util.mapper.ActionMapper;
import at.fischers.controlpagebackend.util.mapper.FieldMapper;
import lombok.*;

import javax.persistence.*;

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
}
