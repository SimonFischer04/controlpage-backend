package at.fischers.controlpagebackend.util.mapper;

import at.fischers.controlpagebackend.dto.action.Action;
import at.fischers.controlpagebackend.dto.action.RestAction;
import at.fischers.controlpagebackend.dto.action.ViewAction;
import at.fischers.controlpagebackend.entity.action.ActionEntity;
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
        // TODO: implement
        return null;
    }

    /**
     * Map a ViewActionEntity to a ViewAction
     *
     * @param viewActionEntity: the ViewActionEntity to map
     * @return the mapped ViewAction
     */
    private static ViewAction mapViewActionEntityToDTO(ViewActionEntity viewActionEntity) {
        // TODO: implement
        return null;
    }

    /**
     * Map a ActionEntity to an Action.
     *
     * @param actionEntity: the ActionEntity of type x to map
     * @return the mapped Action of type x
     */
    public static Action mapEntityToDTO(ActionEntity actionEntity) {
        // TODO: use mapper methods
        if (actionEntity instanceof RestActionEntity) {
            return new RestAction((RestActionEntity) actionEntity);
        }
        if (actionEntity instanceof ViewActionEntity) {
            return new ViewAction((ViewActionEntity) actionEntity);
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
        // TODO: implement
        return null;
    }

    /**
     * Map a ViewAction to a ViewActionEntity
     *
     * @param viewAction: the ViewAction to map
     * @return the mapped ViewActionEntity
     */
    private static ViewActionEntity mapViewActionDTOEntity(ViewAction viewAction) {
        // TODO: implement
        return null;
    }

    /**
     * Map a Action to an ActionEntity.
     *
     * @param action: the Action of type x to map
     * @return the mapped ActionEntity of type x
     */
    public static ActionEntity mapDTOToEntity(Action action) {
        // TODO: implement
        return null;
    }
}
