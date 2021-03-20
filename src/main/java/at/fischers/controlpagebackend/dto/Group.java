package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.util.GroupMapper;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Group {
    private int id;

    @JsonBackReference(value = "parent-group")
    @ToString.Exclude
    private List<Group> childGroups = new ArrayList<>();

    @JsonManagedReference(value = "parent-group")
    private Group parentGroup;

    private String name;

    @JsonBackReference(value = "viewGroup")
    @ToString.Exclude
    private BasicView view;

    public Group(Group group) {
        this.id = group.id;
        this.childGroups = group.childGroups;
        this.parentGroup = group.parentGroup;
        this.name = group.name;
    }

    public Group(GroupEntity groupEntity) {
        this(GroupMapper.mapEntityToDTO(groupEntity));
    }

    /*
        Because they are stored in a database two Groups with the same id are considered equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
