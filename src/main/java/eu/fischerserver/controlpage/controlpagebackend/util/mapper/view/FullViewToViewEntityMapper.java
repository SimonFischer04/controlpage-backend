package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.field.FieldToFieldEntityMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
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

    /**
     * Manual After-mapping method to set the back-reference from FieldEntity to ViewEntity, because can't find a way to do this recursive mapping automagically.
     * @param viewEntity: the {@link ViewEntity} to fix the back-references for
     */
    @AfterMapping
    default void setFieldEntityViewReference(@MappingTarget ViewEntity viewEntity) {
        if (viewEntity != null && viewEntity.getFields() != null) {
            viewEntity.getFields().forEach(field -> field.setView(viewEntity));
        }
    }
}
