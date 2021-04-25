package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.util.mapper.ViewMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "view")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private GroupEntity group;

    @OneToMany(mappedBy = "view", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FieldEntity> fields;

    public ViewEntity(ViewEntity viewEntity) {
        id = viewEntity.getId();
        name = viewEntity.getName();
        group = viewEntity.getGroup();
        fields = viewEntity.getFields();
    }

    public ViewEntity(BasicView view) {
        this(ViewMapper.mapDTOToEntity(view));
    }

    /*
        Because they are stored in a database two Views with the same id are considered equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewEntity view = (ViewEntity) o;
        return id == view.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
