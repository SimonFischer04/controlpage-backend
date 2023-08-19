package at.fischers.controlpagebackend.model.domain;

import at.fischers.controlpagebackend.model.domain.action.Action;
import at.fischers.controlpagebackend.model.domain.text.StyledText;
import at.fischers.controlpagebackend.model.domain.view.FullView;
import at.fischers.controlpagebackend.model.entity.FieldEntity;
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

    private StyledText title;

    private String description;

    private Image background;

    private int rowspan;
    private int colspan;

    public Field(Field field) {
        id = field.getId();
        view = field.getView();
        action = field.getAction();
        title = field.getTitle();
        description = field.getDescription();
        background = field.getBackground();
        rowspan = field.getRowspan();
        colspan = field.getColspan();
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
