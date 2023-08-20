package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.field.FieldToFieldEntityMapper;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class, uses = FieldToFieldEntityMapper.class)
public interface FullViewToViewEntityMapper extends Converter<FullView, ViewEntity> {
    /**
     * Map a {@link FullView} to {@link ViewEntity}
     *
     * @param fullView: the {@link FullView} to map
     * @return the mapped {@link ViewEntity}
     */
    @Override
    ViewEntity convert(@Nullable FullView fullView);
}
