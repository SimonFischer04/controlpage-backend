package at.fischers.controlpagebackend.dto.action;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.enums.ActionType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import at.fischers.controlpagebackend.enums.ViewActionType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewAction extends Action{
    private ViewActionType viewActionType;
    private int viewId;

    @SuppressWarnings("unused")
    @Builder
    public ViewAction(int id, Field field, ActionType actionType, RunPolicy runPolicy, ViewActionType viewActionType, int viewId) {
        super(id, field, actionType, runPolicy);
        this.viewActionType = viewActionType;
        this.viewId = viewId;
    }
}
