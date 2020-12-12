package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.entity.GroupEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Group {
    private int id;

    @JsonManagedReference
    private List<Group> childGroups = new ArrayList<>();

    @JsonBackReference
    private Group parentGroup;
    private String name;

    private void addChildGroup(Group group) {
        group.setParentGroup(this);
        childGroups.add(group);
    }

    public Group(GroupEntity groupEntity) {
        id = groupEntity.getId();
        groupEntity.getChildGroups().forEach(e -> addChildGroup(new Group(e)));
        name = groupEntity.getName();
    }
}
