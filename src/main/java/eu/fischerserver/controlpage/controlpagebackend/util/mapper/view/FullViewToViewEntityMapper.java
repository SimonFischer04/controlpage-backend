package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.field.FieldToFieldEntityMapper;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.group.GroupToGroupEntityMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        config = MapperSpringConfig.class,
        uses = {BasicViewToViewEntityMapper.class, FieldToFieldEntityMapper.class, GroupToGroupEntityMapper.class}
)
public interface FullViewToViewEntityMapper extends Converter<FullView, ViewEntity> {
    /**
     * Map a {@link FullView} to {@link ViewEntity}
     *
     * @param fullView: the {@link FullView} to map
     * @return the mapped {@link ViewEntity}
     */
    @Override
    @Mappings({
            // avoid recursive view -> group -> view -> ... mapping
            @Mapping(target = "group", source = "group", qualifiedByName = "GroupWithoutViewsMapper")
    })
    ViewEntity convert(@Nullable FullView fullView);

    /**
     * Manual After-mapping method to set the back-reference from FieldEntity to ViewEntity, because can't find a way to do this recursive mapping automagically.
     *
     * @param viewEntity: the {@link ViewEntity} to fix the back-references for
     */
    @AfterMapping
    default void setFieldEntityViewBackReference(@MappingTarget ViewEntity viewEntity) {
        if (viewEntity == null || viewEntity.getFields() == null) {
            return;
        }

        // Caused by: java.util.ConcurrentModificationException
        // viewEntity.getFields().stream().forEach(field -> field.setView(viewEntity));

        List<FieldEntity> newFields = new ArrayList<>();
        viewEntity.getFields().forEach(field -> {
            newFields.add(field.toBuilder().view(viewEntity).build());
        });
        viewEntity.setFields(newFields);
    }
}
