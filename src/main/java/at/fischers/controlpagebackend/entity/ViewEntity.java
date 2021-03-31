package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "view")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private GroupEntity group;

    @OneToMany(mappedBy = "view", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FieldEntity> fields;

    public ViewEntity(BasicView view) {
        id = view.getId();
        name = view.getName();
        group = new GroupEntity(view.getGroup());
        group.setView(this);
    }

    public ViewEntity(FullView view) {
        this((BasicView) view);
        fields = new ArrayList<>();
        int y = 0;
        for (Collection<Field> row : view.getFields()) {
            int x = 0;
            for (Field field : row) {
                FieldEntity fieldEntity = new FieldEntity(field);
                fieldEntity.setView(this);
                fieldEntity.setYPos(y);
                fieldEntity.setXPos(x);
                fields.add(fieldEntity);
                x++;
            }
            y++;
        }
    }
}
