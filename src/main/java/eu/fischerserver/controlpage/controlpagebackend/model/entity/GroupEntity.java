package eu.fischerserver.controlpage.controlpagebackend.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "view_group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<GroupEntity> childGroups;

    @ManyToOne(cascade = CascadeType.ALL)
    private GroupEntity parentGroup;

    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ViewEntity> views;

    public GroupEntity(GroupEntity groupEntity) {
        id = groupEntity.getId();
        childGroups = groupEntity.getChildGroups();
        parentGroup = groupEntity.getParentGroup();
        name = groupEntity.getName();
        views = groupEntity.getViews();
    }

    /*
        Because they are stored in a database two Groups with the same id are considered equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity group = (GroupEntity) o;
        return id == group.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
