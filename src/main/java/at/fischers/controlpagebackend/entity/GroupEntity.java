package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.util.mapper.groupmapper.GroupMapperDTOToEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "view_group")
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @OneToOne(mappedBy = "group")
    @ToString.Exclude
    private ViewEntity view;

    public GroupEntity(GroupEntity groupEntity) {
        id = groupEntity.getId();
        childGroups = groupEntity.getChildGroups();
        parentGroup = groupEntity.getParentGroup();
        name = groupEntity.getName();
        view = groupEntity.getView();
    }

    public GroupEntity(Group group) {
        this(GroupMapperDTOToEntity.mapDTOToEntity(group));
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
