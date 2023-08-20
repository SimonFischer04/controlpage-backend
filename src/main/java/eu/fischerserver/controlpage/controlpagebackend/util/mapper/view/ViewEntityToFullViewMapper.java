package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.field.FieldEntityToFieldMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class, uses = FieldEntityToFieldMapper.class)
public interface ViewEntityToFullViewMapper extends Converter<ViewEntity, FullView> {
    /**
     * Map a {@link ViewEntity} to a {@link FullView}
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link FullView}
     */
    @Override
//    @Mapping(target = "fields", ignore = true)
//    @BeanMapping(ignoreUnmappedSourceProperties = {"fields"})
    @Mapping(target = "fields", source = "fields")
    FullView convert(@Nullable ViewEntity viewEntity);
}
