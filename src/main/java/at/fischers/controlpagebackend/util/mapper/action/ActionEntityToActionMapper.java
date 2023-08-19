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
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class)
public interface ActionEntityToActionMapper extends Converter<ActionEntity, Action> {

    /*
      Map a DesktopAutomationActionEntity to a DesktopAutomationAction

      @param desktopAutomationActionEntity: the DesktopAutomationActionEntity to map
     * @return the mapped DesktopAutomationAction
     */


    /*
      Map a RestActionEntity to a RestAction

      @param restActionEntity: the RestActionEntity to map
     * @return the mapped RestAction
     */


    /*
      Map a ViewActionEntity to a ViewAction

      @param viewActionEntity: the ViewActionEntity to map
     * @return the mapped ViewAction
     */


    /**
     * Map a ActionEntity to an Action.
     *
     * @param actionEntity: the ActionEntity of type x to map
     * @return the mapped Action of type x
     */
    @Override
    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    @SubclassMapping(source = DesktopAutomationActionEntity.class, target = DesktopAutomationAction.class)
    @SubclassMapping(source = RestActionEntity.class, target = RestAction.class)
    @SubclassMapping(source = ViewActionEntity.class, target = ViewAction.class)
    Action convert(@Nullable ActionEntity actionEntity);
}
