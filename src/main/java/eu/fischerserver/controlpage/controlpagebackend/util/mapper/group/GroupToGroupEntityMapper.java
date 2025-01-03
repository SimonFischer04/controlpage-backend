package eu.fischerserver.controlpage.controlpagebackend.util.mapper.group;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.view.BasicViewToViewEntityMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class, uses = BasicViewToViewEntityMapper.class)
public interface GroupToGroupEntityMapper extends Converter<Group, GroupEntity> {
    /**
     * Map a Group to a GroupEntity.
     *
     * @param group: the GroupEntity to map
     * @return the mapped GroupEntity
     */
    @Override
    // Unmapped target property: "fields". Mapping from Collection element "BasicView views" to "ViewEntity views".
    // => can't map them / not needed, but also can't ignore=true them properly because part of collection
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mappings({
            @Mapping(source = "parentGroup", target = "parentGroup", qualifiedByName = "ParentMapper"),
            @Mapping(source = "childGroups", target = "childGroups", qualifiedByName = "ChildMapper"),
            @Mapping(source = "views", target = "views", qualifiedByName = "BasicViewToViewEntityWithoutGroupMapper")
    })
    GroupEntity convert(@Nullable Group group);

    @Named("GroupWithoutViewsMapper")
    @Mappings({
            @Mapping(source = "views", target = "views", ignore = true)
    })
    @InheritConfiguration(name = "convert")
    GroupEntity convertWithoutViews(@Nullable Group group);

    @Named("ParentMapper")
    @InheritConfiguration(name = "convert")
    @Mappings({
            @Mapping(target = "parentGroup", qualifiedByName = "ParentMapper"),
            @Mapping(target = "childGroups", source = "childGroups", ignore = true)
    })
    GroupEntity mapParents(@Nullable Group group);

    @Named("ChildMapper")
    @InheritConfiguration(name = "convert")
    @Mappings({
            @Mapping(target = "childGroups", qualifiedByName = "ChildMapper"),
            @Mapping(target = "parentGroup", source = "parentGroup", ignore = true)
    })
    GroupEntity mapChildren(@Nullable Group group);

    @AfterMapping
    default void setViewEntityGroupBackReference(@MappingTarget GroupEntity groupEntity) {
        if (groupEntity == null || groupEntity.getViews() == null) {
            return;
        }

        groupEntity.getViews().forEach(view -> view.setGroup(groupEntity));
    }
}
