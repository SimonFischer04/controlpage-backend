package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.util.mapper.ActionMapper;
import at.fischers.controlpagebackend.util.mapper.FieldMapper;
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

    public Field(Field field) {
        id = field.getId();
        view = field.getView();
        action = field.getAction();
        title = field.getTitle();
        background = field.getBackground();
        rowspan = field.getRowspan();
        colspan = field.getColspan();
    }

    public Field(FieldEntity fieldEntity) {
        this(FieldMapper.mapEntityToDTO(fieldEntity));
    }
}
