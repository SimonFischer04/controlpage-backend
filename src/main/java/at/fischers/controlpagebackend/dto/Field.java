package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.util.ActionMapper;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Field {
    private int id;

    @JsonBackReference
    private FullView view;

    @JsonManagedReference
    private Action action;

    private String background;
    private int rowspan;
    private int colspan;

    public Field(FieldEntity fieldEntity) {
        id = fieldEntity.getId();
        action = ActionMapper.mapEntityToDTO(fieldEntity.getAction());
        action.setField(this);
        background = fieldEntity.getBackground();
        rowspan = fieldEntity.getRowspan();
        colspan = fieldEntity.getColspan();
    }
}
