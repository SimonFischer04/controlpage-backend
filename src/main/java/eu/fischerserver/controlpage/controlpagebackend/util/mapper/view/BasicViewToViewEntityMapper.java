package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.group.GroupToGroupEntityMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.Collections;

//@Mapper(config = MapperSpringConfig.class, uses = GroupToGroupEntityMapper.class)
@Mapper(config = MapperSpringConfig.class)
public interface BasicViewToViewEntityMapper extends Converter<BasicView, ViewEntity> {
    // TODO: javadoc
    @Override
    @Mappings({
            // BasicView does not have fields
            @Mapping(target = "fields", ignore = true),

            // TODO: Fix this: importing GroupToGroupEntityMapper would create recursive dep
            // avoid recursive view -> group -> view -> ... mapping
//            @Mapping(target = "group", source = "group", qualifiedByName = "GroupWithoutViewsMapper")
    })
    ViewEntity convert(@Nullable BasicView basicView);

    @Named("BasicViewToViewEntityWithoutGroupMapper")
    @InheritConfiguration(name = "convert")
    @Mappings({
            @Mapping(target = "group", source = "group", ignore = true)
    })
    ViewEntity convertWithoutGroup(@Nullable BasicView basicView);

    @AfterMapping
    default void setGroupEntityViewBackReference(@MappingTarget ViewEntity viewEntity) {
        if (viewEntity == null || viewEntity.getGroup() == null)
            return;

        // already set correctly
        if (viewEntity.getGroup().getViews() != null)
            return;

        viewEntity.getGroup().setViews(Collections.singletonList(viewEntity));
    }
}
