package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.group.GroupEntityToGroupMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.Collections;

//@Mapper(config = MapperSpringConfig.class, uses = GroupEntityToGroupMapper.class)
@Mapper(config = MapperSpringConfig.class)
public interface ViewEntityToBasicViewMapper extends Converter<ViewEntity, BasicView> {
    /**
     * Map a {@link ViewEntity} to a {@link BasicView}.
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link BasicView}
     */
    @Override
    @BeanMapping(resultType = BasicView.class, ignoreUnmappedSourceProperties = "fields")
    @Mappings({
            // TODO: Fix this: importing GroupToGroupEntityMapper would create recursive dep
            // avoid recursive view -> group -> view -> ... mapping
//            @Mapping(target = "group", source = "group", qualifiedByName = "GroupWithoutViewsMapper")
    })
    BasicView convert(@Nullable ViewEntity viewEntity);

    @Named("ViewEntityToBasicViewWithoutGroupMapper")
    @BeanMapping(ignoreUnmappedSourceProperties = "fields")
    @Mappings({
            @Mapping(target = "group", source = "group", ignore = true)
    })
    BasicView convertWithoutGroup(@Nullable ViewEntity viewEntity);

    @AfterMapping
    default void setGroupViewBackReference(@MappingTarget BasicView view) {
        if (view == null || view.getGroup() == null)
            return;

        // already set correctly
        if (view.getGroup().views() != null)
            return;

        view.setGroup(view.getGroup().toBuilder()
                .views(Collections.singletonList(view))
                .build());
    }
}
