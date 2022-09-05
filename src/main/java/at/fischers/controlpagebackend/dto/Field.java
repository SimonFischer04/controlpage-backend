package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.util.mapper.ActionMapper;
import at.fischers.controlpagebackend.util.mapper.FieldMapper;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.Objects;

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
    private String description;
    private int backgroundId;
    private int rowspan;
    private int colspan;

    public Field(Field field) {
        id = field.getId();
        view = field.getView();
        action = field.getAction();
        title = field.getTitle();
        description = field.getDescription();
        backgroundId = field.getBackgroundId();
        rowspan = field.getRowspan();
        colspan = field.getColspan();
    }

    public Field(FieldEntity fieldEntity) {
        this(FieldMapper.mapEntityToDTO(fieldEntity));
    }

    /*
        Because they are stored in a database two Fields with the same id are considered equals
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return id == field.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
