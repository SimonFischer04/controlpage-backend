package at.fischers.controlpagebackend.dto.view;

import at.fischers.controlpagebackend.dto.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FullViewDTO extends BasicViewDTO {
    private List<List<Field>> fields;

    public FullViewDTO(FullView fullView) {
        super(fullView);
        this.fields = fullView.getFields();
    }
}
