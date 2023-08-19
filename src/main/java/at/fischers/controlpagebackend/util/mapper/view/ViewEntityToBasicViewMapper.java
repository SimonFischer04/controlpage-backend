package at.fischers.controlpagebackend.util.mapper.view;

import at.fischers.controlpagebackend.config.MapperSpringConfig;
import at.fischers.controlpagebackend.model.domain.Group;
import at.fischers.controlpagebackend.model.domain.view.FullView;
import at.fischers.controlpagebackend.model.entity.FieldEntity;
import at.fischers.controlpagebackend.model.entity.ViewEntity;
import at.fischers.controlpagebackend.model.domain.Field;
import at.fischers.controlpagebackend.model.domain.view.BasicView;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.List;

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
