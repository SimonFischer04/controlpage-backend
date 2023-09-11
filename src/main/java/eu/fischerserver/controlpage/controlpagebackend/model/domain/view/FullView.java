package eu.fischerserver.controlpage.controlpagebackend.model.domain.view;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FullView extends BasicView {
    private List<List<Field>> fields;

    @SuppressWarnings("unused")
    @Builder(builderMethodName = "fullBuilder")
    public FullView(int id, String name, Group group, List<List<Field>> fields) {
        super(id, name, group);
        this.fields = fields;
    }
}
