package at.fischers.controlpagebackend.dto;

import at.fischers.controlpagebackend.entity.action.ActionEntity;
import at.fischers.controlpagebackend.entity.action.ActionType;
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
    private String actionValue;

    public Action(ActionEntity actionEntity) {
        id = actionEntity.getId();
        actionType = actionEntity.getActionType();
        actionValue = actionEntity.getActionValue();
    }
}
