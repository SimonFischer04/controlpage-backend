package at.fischers.controlpagebackend.dto.action;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.action.ActionEntity;
import at.fischers.controlpagebackend.enums.ActionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Action {
    private int id;

    @JsonBackReference
    private Field field;

    private ActionType actionType;

    public Action(ActionEntity entity) {
        id = entity.getId();
    }
}
