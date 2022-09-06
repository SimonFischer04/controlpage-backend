package at.fischers.controlpagebackend.util.mapper;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.dto.action.DesktopAutomationAction;
import at.fischers.controlpagebackend.dto.action.RestAction;
import at.fischers.controlpagebackend.dto.action.ViewAction;
import at.fischers.controlpagebackend.entity.action.ActionEntity;
import at.fischers.controlpagebackend.entity.action.DesktopAutomationActionEntity;
import at.fischers.controlpagebackend.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.entity.action.ViewActionEntity;

public class ActionMapper {
    /**
     * Map a RestActionEntity to a RestAction
     *
     * @param restActionEntity: the RestActionEntity to map
     * @return the mapped RestAction
     */
    private static RestAction mapRestActionEntityToDTO(RestActionEntity restActionEntity) {
        return RestAction.builder()
                .id(restActionEntity.getId())
                .restType(restActionEntity.getType())
                .body(restActionEntity.getBody())
                // NOTE: this must be set by the Field mapper (otherwise would create an infinity recursion)
                .field(null)
                .runPolicy(restActionEntity.getRunPolicy())
                .url(restActionEntity.getUrl())
                .build();
    }

    /**
     * Map a ViewActionEntity to a ViewAction
     *
     * @param viewActionEntity: the ViewActionEntity to map
     * @return the mapped ViewAction
     */
    private static ViewAction mapViewActionEntityToDTO(ViewActionEntity viewActionEntity) {
        return ViewAction.builder()
                .id(viewActionEntity.getId())
                .viewActionType(viewActionEntity.getType())
                .viewId(viewActionEntity.getViewId())
                .runPolicy(viewActionEntity.getRunPolicy())
                // NOTE: this must be set by the Field mapper (otherwise would create an infinity recursion)
                .field(null)
                .build();
    }

    private static DesktopAutomationAction mapDesktopAutomationActionEntityToDTO(DesktopAutomationActionEntity desktopAutomationActionEntity) {
        return DesktopAutomationAction.builder()
                .id(desktopAutomationActionEntity.getId())
                .functionName(desktopAutomationActionEntity.getFunctionName())
                .runPolicy(desktopAutomationActionEntity.getRunPolicy())
                // NOTE: this must be set by the Field mapper (otherwise would create an infinity recursion)
                .field(null)
                .build();
    }

    /**
     * Map a ActionEntity to an Action.
     *
     * @param actionEntity: the ActionEntity of type x to map
     * @return the mapped Action of type x
     */
    public static Action mapEntityToDTO(ActionEntity actionEntity) {
        if (actionEntity == null)
            return null;

        if (actionEntity instanceof RestActionEntity) {
            return mapRestActionEntityToDTO((RestActionEntity) actionEntity);
        }
        if (actionEntity instanceof ViewActionEntity) {
            return mapViewActionEntityToDTO((ViewActionEntity) actionEntity);
        }
        if (actionEntity instanceof DesktopAutomationActionEntity) {
            return mapDesktopAutomationActionEntityToDTO((DesktopAutomationActionEntity) actionEntity);
        }
        return null;
    }


    /*
        ----------------------------------------------------------------------------
     */

    /**
     * Map a RestAction to a RestActionEntity
     *
     * @param restAction: the RestAction to map
     * @return the mapped RestActionEntity
     */
    private static RestActionEntity mapRestActionDTOToEntity(RestAction restAction) {
        return RestActionEntity.builder()
                .id(restAction.getId())
                .body(restAction.getBody())
                .type(restAction.getRestType())
                .url(restAction.getUrl())
                .runPolicy(restAction.getRunPolicy())
                // NOTE: this must be set by the Field mapper (otherwise would create an infinity recursion)
                .field(null)
                .build();
    }

    /**
     * Map a ViewAction to a ViewActionEntity
     *
     * @param viewAction: the ViewAction to map
     * @return the mapped ViewActionEntity
     */
    private static ViewActionEntity mapViewActionDTOToEntity(ViewAction viewAction) {
        return ViewActionEntity.builder()
                .id(viewAction.getId())
                .viewId(viewAction.getViewId())
                .type(viewAction.getViewActionType())
                .runPolicy(viewAction.getRunPolicy())
                // NOTE: this must be set by the Field mapper (otherwise would create an infinity recursion)
                .field(null)
                .build();
    }

    private static DesktopAutomationActionEntity mapViewActionDTOToEntity(DesktopAutomationAction desktopAutomationAction) {
        return DesktopAutomationActionEntity.builder()
                .id(desktopAutomationAction.getId())
                .functionName(desktopAutomationAction.getFunctionName())
                .runPolicy(desktopAutomationAction.getRunPolicy())
                // NOTE: this must be set by the Field mapper (otherwise would create an infinity recursion)
                .field(null)
                .build();
    }

    /**
     * Map a Action to an ActionEntity.
     *
     * @param action: the Action of type x to map
     * @return the mapped ActionEntity of type x
     */
    public static ActionEntity mapDTOToEntity(Action action) {
        if (action == null)
            return null;

        if (action instanceof RestAction) {
            return mapRestActionDTOToEntity((RestAction) action);
        }
        if (action instanceof ViewAction) {
            return mapViewActionDTOToEntity((ViewAction) action);
        }
        if (action instanceof DesktopAutomationAction) {
            return mapViewActionDTOToEntity((DesktopAutomationAction) action);
        }
        return null;
    }
}
