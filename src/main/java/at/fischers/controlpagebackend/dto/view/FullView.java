package at.fischers.controlpagebackend.dto.view;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.ViewEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullView extends BasicView {
    @JsonManagedReference
    private List<Field> fields = new ArrayList<>();

    private void addField(Field field) {
        fields.add(field);
        field.setView(this);
    }

    public FullView(ViewEntity view) {
        super(view);
        view.getFields().forEach(fieldEntity -> addField(new Field(fieldEntity)));
    }
}
