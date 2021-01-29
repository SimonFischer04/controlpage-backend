package at.fischers.controlpagebackend.dto.action;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.action.ActionEntity;
import at.fischers.controlpagebackend.enums.ActionType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Action {
    private int id;

    @ToString.Exclude
    @JsonIgnore
    private Field field;

    private ActionType actionType;

    private RunPolicy runPolicy;

    public Action(ActionEntity entity) {
        id = entity.getId();
        runPolicy = entity.getRunPolicy();
    }
}
