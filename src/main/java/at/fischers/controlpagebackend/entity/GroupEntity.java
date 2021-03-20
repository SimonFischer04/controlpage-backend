package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.dto.Group;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "view_group")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL)
    private List<GroupEntity> childGroups;

    @ManyToOne
    @ToString.Exclude
    private GroupEntity parentGroup;

    private String name;

    @OneToOne
    private ViewEntity view;

    public GroupEntity(Group group) {
        if (group != null) {
            id = group.getId();
            if (group.getChildGroups() != null) {
                childGroups = new ArrayList<>();
                group.getChildGroups().forEach(grp -> {
                    GroupEntity groupEntity = new GroupEntity(grp);
                    groupEntity.setParentGroup(this);
                    childGroups.add(groupEntity);
                });
            }
            name = group.getName();
        }
    }
}
