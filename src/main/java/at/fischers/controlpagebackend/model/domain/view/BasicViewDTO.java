package at.fischers.controlpagebackend.model.domain.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasicViewDTO {
    private int id;
    private String name;

    private int groupId;

    public BasicViewDTO(BasicView basicView) {
        this.id = basicView.getId();
        this.name = basicView.getName();
        var group = basicView.getGroup();
        this.groupId = group == null ? -1 : group.getId();
    }
}
