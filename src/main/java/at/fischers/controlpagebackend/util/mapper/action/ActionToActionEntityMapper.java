package at.fischers.controlpagebackend.util.mapper.action;

import at.fischers.controlpagebackend.config.MapperSpringConfig;
import at.fischers.controlpagebackend.model.domain.action.DesktopAutomationAction;
import at.fischers.controlpagebackend.model.domain.action.RestAction;
import at.fischers.controlpagebackend.model.domain.action.ViewAction;
import at.fischers.controlpagebackend.model.entity.action.ActionEntity;
import at.fischers.controlpagebackend.model.domain.action.Action;
import at.fischers.controlpagebackend.model.entity.action.DesktopAutomationActionEntity;
import at.fischers.controlpagebackend.model.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.model.entity.action.ViewActionEntity;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class)
public interface ActionToActionEntityMapper extends Converter<Action, ActionEntity> {
    /*
      Map a DesktopAutomationAction to a DesktopAutomationActionEntity

      @param desktopAutomationAction: the DesktopAutomationAction to map
     * @return the mapped DesktopAutomationActionEntity
     */

    /*
      Map a RestAction to a RestActionEntity

      @param restAction: the RestAction to map
     * @return the mapped RestActionEntity
     */

    /*
      Map a ViewAction to a ViewActionEntity

      @param viewAction: the ViewAction to map
     * @return the mapped ViewActionEntity
     */

    /**
     * Map a Action to an ActionEntity.
     *
     * @param action: the Action of type x to map
     * @return the mapped ActionEntity of type x
     */
    @Override
    @SubclassMapping(source = DesktopAutomationAction.class, target = DesktopAutomationActionEntity.class)
    @SubclassMapping(source = RestAction.class, target = RestActionEntity.class)
    @SubclassMapping(source = ViewAction.class, target = ViewActionEntity.class)
    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    ActionEntity convert(@Nullable Action action);
}
