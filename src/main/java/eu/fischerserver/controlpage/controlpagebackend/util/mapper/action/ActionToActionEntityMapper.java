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

    // TODO: inherit broken for some reason
    // @InheritConfiguration(name = "convert")
    @SubclassMapping(source = DesktopAutomationAction.class, target = DesktopAutomationActionEntity.class)
    @SubclassMapping(source = RestAction.class, target = RestActionEntity.class)
    @SubclassMapping(source = ViewAction.class, target = ViewActionEntity.class)
    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    @Named("ActionToActionEntityWithoutBackReferencesMapper")
    @Mappings({
            @Mapping(target = "field", source = "field", ignore = true)
    })
    ActionEntity mapActionToActionEntityWithoutBackReferences(@Nullable Action action);
}
