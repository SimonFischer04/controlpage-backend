package eu.fischerserver.controlpage.controlpagebackend.util.mapper.group;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.view.ViewEntityToBasicViewMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapperSpringConfig.class, uses = ViewEntityToBasicViewMapper.class)
public interface GroupEntityToGroupMapper extends Converter<GroupEntity, Group> {
    /**
     * Map a GroupEntity to a Group.
     *
     * @param groupEntity: the GroupEntity to map
     * @return the mapped Group
     */
    @Override
    @Mappings({
            @Mapping(source = "parentGroup", target = "parentGroup", qualifiedByName = "ParentMapper"),
            @Mapping(source = "childGroups", target = "childGroups", qualifiedByName = "ChildMapper"),
            @Mapping(source = "views", target = "views", qualifiedByName = "ViewEntityToBasicViewWithoutGroupMapper")
    })
    Group convert(@Nullable GroupEntity groupEntity);

    @Named("GroupWithoutViewsMapper")
    @Mappings({
            @Mapping(source = "views", target = "views", ignore = true)
    })
    @InheritConfiguration(name = "convert")
    Group convertWithoutViews(@Nullable GroupEntity groupEntity);

    @Named("ParentMapper")
    @InheritConfiguration(name = "convert")
    @Mappings({
            @Mapping(target = "parentGroup", qualifiedByName = "ParentMapper"),
            @Mapping(target = "childGroups", source = "childGroups", ignore = true)
    })
    Group mapParents(@Nullable GroupEntity group);

    @Named("ChildMapper")
    @InheritConfiguration(name = "convert")
    @Mappings({
            @Mapping(target = "childGroups", qualifiedByName = "ChildMapper"),
            @Mapping(target = "parentGroup", source = "parentGroup", ignore = true)
    })
    Group mapChildren(@Nullable GroupEntity group);

    @AfterMapping
    default void setViewGroupBackReference(@MappingTarget Group group) {
        if (group == null || group.views() == null) {
            return;
        }

        // Caused by: java.util.ConcurrentModificationException
        // group.views().forEach(view -> view.setGroup(group));
        List<BasicView> newViews = new ArrayList<>();
        group.views().forEach(view -> {
            newViews.add(view.toBuilder()
                    .group(group)
                    .build());
        });
        group.views().clear();
        group.views().addAll(newViews);
    }
}
