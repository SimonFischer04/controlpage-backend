package eu.fischerserver.controlpage.controlpagebackend.util.mapper.group;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.view.ViewEntityToBasicViewMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

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
            // in fact, group still gets mapped properly throughout the tree because group -> field mapping already sets field.group?
            // TODO: investigate this further
            @Mapping(source = "views", target = "views", qualifiedByName = "ViewEntityToBasicViewWithoutGroupMapper")
    })
    Group convert(@Nullable GroupEntity groupEntity);

    @Named("ParentMapper")
    @InheritConfiguration(name = "convert")
    @Mappings({
            @Mapping(target = "parentGroup", qualifiedByName = "ParentMapper"),
            // Set inside GroupToGroupEntityMapperDecorator. TODO: better way?
            @Mapping(target = "childGroups", source = "childGroups", ignore = true)
    })
    Group mapParents(@Nullable GroupEntity group);

    @Named("ChildMapper")
    @InheritConfiguration(name = "convert")
    @Mappings({
            @Mapping(target = "childGroups", qualifiedByName = "ChildMapper"),
            // TODO: parent seems to be set anyways? => analyse this
            @Mapping(target = "parentGroup", source = "parentGroup", ignore = true)
    })
    Group mapChildren(@Nullable GroupEntity group);
}
