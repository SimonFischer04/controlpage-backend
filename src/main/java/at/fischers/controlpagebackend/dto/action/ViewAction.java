package at.fischers.controlpagebackend.dto.action;

import at.fischers.controlpagebackend.entity.action.ViewActionEntity;
import at.fischers.controlpagebackend.enums.ActionType;
import at.fischers.controlpagebackend.enums.ViewActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewAction extends Action{
    private ViewActionType viewActionType;
    private int viewId;

    public ViewAction(ViewActionEntity entity){
        super(entity);
        setActionType(ActionType.VIEW);
        viewActionType = entity.getType();
        viewId = entity.getViewId();
    }
}
