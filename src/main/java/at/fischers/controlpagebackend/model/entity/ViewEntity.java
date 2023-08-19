package at.fischers.controlpagebackend.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "view")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ToString.Exclude
    private GroupEntity group;

    @OneToMany(mappedBy = "view", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FieldEntity> fields;

    public ViewEntity(ViewEntity viewEntity) {
        id = viewEntity.getId();
        name = viewEntity.getName();
        group = viewEntity.getGroup();
        fields = viewEntity.getFields();
    }

//    public ViewEntity(ImageService imageService, BasicView view) {
//        this(ViewMapper.mapDTOToEntity(imageService, view));
//    }

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
