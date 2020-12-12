package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.entity.ViewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasicView {
    private int id;
    private String name;
    private Group group;

    public BasicView(ViewEntity view) {
        id = view.getId();
        name = view.getName();
        group = new Group(view.getGroup());
    }
}
