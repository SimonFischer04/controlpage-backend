package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.field.FieldEntityToFieldMapper;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.group.GroupEntityToGroupMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        config = MapperSpringConfig.class,
        uses = {ViewEntityToBasicViewMapper.class, FieldEntityToFieldMapper.class, GroupEntityToGroupMapper.class}
)
public interface ViewEntityToFullViewMapper extends Converter<ViewEntity, FullView> {
    /**
     * Map a {@link ViewEntity} to a {@link FullView}
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link FullView}
     */
    @Override
    @Mappings({
            // avoid recursive view -> group -> view -> ... mapping
            @Mapping(target = "group", source = "group", qualifiedByName = "GroupWithoutViewsMapper")
    })
    FullView convert(@Nullable ViewEntity viewEntity);

    /**
     * Manual After-mapping method to set the back-reference from Field to FullView, because can't find a way to do this recursive mapping automagically.
     * @param fullView: the {@link FullView} to fix the back-references for
     */
    @AfterMapping
    default void setFieldViewReference(@MappingTarget FullView fullView) {
        if(fullView == null || fullView.getFields() == null) {
            return;
        }

        List<List<Field>> newFields = new ArrayList<>();
        fullView.getFields().forEach(row -> {
            List<Field> newFieldRow = new ArrayList<>();
            row.forEach(field -> {
                // record is immutable
                // field.setView(fullView);
                newFieldRow.add(field.toBuilder().view(fullView).build());
            });
            newFields.add(newFieldRow);
        });
        fullView.setFields(newFields);
    }
}
