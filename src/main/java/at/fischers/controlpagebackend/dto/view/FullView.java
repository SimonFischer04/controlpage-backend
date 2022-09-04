package at.fischers.controlpagebackend.dto.view;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.service.GroupService;
import at.fischers.controlpagebackend.util.mapper.ViewMapper;
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

    public FullView(ViewEntity view) {
        this(ViewMapper.mapEntityToFullDTO(view));
    }

    public FullView(GroupService groupService, FullViewDTO fullViewDTO) {
        super(fullViewDTO.getId(), fullViewDTO.getName(), groupService.findById(fullViewDTO.getGroupId()));
        this.fields = fullViewDTO.getFields();
    }
}
