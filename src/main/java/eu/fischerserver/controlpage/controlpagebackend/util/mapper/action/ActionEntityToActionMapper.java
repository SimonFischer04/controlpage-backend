package eu.fischerserver.controlpage.controlpagebackend.util.mapper.action;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.DesktopAutomationAction;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.RestAction;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.ViewAction;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.Action;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.DesktopAutomationActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.RestActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ViewActionEntity;
import org.mapstruct.*;
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
    @SubclassMapping(source = DesktopAutomationActionEntity.class, target = DesktopAutomationAction.class)
    @SubclassMapping(source = RestActionEntity.class, target = RestAction.class)
    @SubclassMapping(source = ViewActionEntity.class, target = ViewAction.class)
    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    Action convert(@Nullable ActionEntity actionEntity);

    // TODO: inherit broken for some reason
    // @InheritConfiguration(name = "convert")
    @SubclassMapping(source = DesktopAutomationActionEntity.class, target = DesktopAutomationAction.class)
    @SubclassMapping(source = RestActionEntity.class, target = RestAction.class)
    @SubclassMapping(source = ViewActionEntity.class, target = ViewAction.class)
    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    @Named("ActionEntityToActionWithoutBackReferencesMapper")
    @Mappings({
            @Mapping(target = "field", source = "field", ignore = true)
    })
    Action mapActionEntityToActionWithoutBackReferences(@Nullable ActionEntity actionEntity);
}
