package at.fischers.controlpagebackend.util;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.dto.action.RestAction;
import at.fischers.controlpagebackend.dto.action.ViewAction;
import at.fischers.controlpagebackend.entity.action.ActionEntity;
import at.fischers.controlpagebackend.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.entity.action.ViewActionEntity;

public class ActionMapper {
    public static Action mapEntityToDTO(ActionEntity actionEntity) {
        if (actionEntity instanceof RestActionEntity) {
            return new RestAction((RestActionEntity) actionEntity);
        }
        if (actionEntity instanceof ViewActionEntity) {
            return new ViewAction((ViewActionEntity) actionEntity);
        }
        return null;
    }
}
