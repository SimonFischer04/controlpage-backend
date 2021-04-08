package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.util.mapper.ActionMapper;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Field {
    private int id;

    @ToString.Exclude
    @JsonIgnore
    private FullView view;

    private Action action;

    private String title;
    private Image background;
    private int rowspan;
    private int colspan;

    public Field(FieldEntity fieldEntity) {
        id = fieldEntity.getId();
        if (fieldEntity.getAction() != null) {
            action = ActionMapper.mapEntityToDTO(fieldEntity.getAction());
            action.setField(this);
        }
        title = fieldEntity.getTitle();
        if (fieldEntity.getBackground() != null) {
            background = new Image(fieldEntity.getBackground());
        }

        rowspan = fieldEntity.getRowspan();
        colspan = fieldEntity.getColspan();
    }
}
