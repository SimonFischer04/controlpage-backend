package at.fischers.controlpagebackend.dto.view;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.ViewEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasicView {
    private int id;
    private String name;

    @JsonManagedReference(value = "viewGroup")
    private Group group;

    public BasicView(ViewEntity view) {
        // TODO: use mapper class
        id = view.getId();
        name = view.getName();
        group = new Group(view.getGroup());
        group.setView(this);
    }
}
