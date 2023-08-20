package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class)
public interface ViewEntityToBasicViewMapper extends Converter<ViewEntity, BasicView> {
    /**
     * Map a {@link ViewEntity} to a {@link BasicView}.
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link BasicView}
     */
    @Override
    @BeanMapping(resultType = BasicView.class, ignoreUnmappedSourceProperties = {
            "fields"
    })
    @Named("ViewEntityToBasicView")
    BasicView convert(@Nullable ViewEntity viewEntity);
}
