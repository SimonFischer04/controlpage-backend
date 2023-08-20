package eu.fischerserver.controlpage.controlpagebackend.model.domain.view;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.service.GroupService;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FullView extends BasicView {
    private List<List<Field>> fields;

    @SuppressWarnings("unused")
    public FullView(FullView fullView) {
        super(fullView);
        fields = fullView.getFields();
    }

    @SuppressWarnings("unused")
    @Builder(builderMethodName = "fullBuilder")
    public FullView(int id, String name, Group group, List<List<Field>> fields) {
        super(id, name, group);
        this.fields = fields;
    }

    public FullView(GroupService groupService, FullViewDTO fullViewDTO) {
        super(fullViewDTO.getId(), fullViewDTO.getName(), groupService.findById(fullViewDTO.getGroupId()));
        this.fields = fullViewDTO.getFields();
    }
}
