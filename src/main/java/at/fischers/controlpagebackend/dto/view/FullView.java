package at.fischers.controlpagebackend.dto.view;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.util.mapper.ViewMapper;
import lombok.*;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
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
}
