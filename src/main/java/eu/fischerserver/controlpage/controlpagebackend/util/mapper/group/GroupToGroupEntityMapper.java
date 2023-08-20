package eu.fischerserver.controlpage.controlpagebackend.util.mapper.group;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.field.FieldToFieldEntityMapper;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.view.BasicViewToViewEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper(config = MapperSpringConfig.class, uses = BasicViewToViewEntityMapper.class)
@DecoratedWith(GroupToGroupEntityMapperDecorator.class)
public interface GroupToGroupEntityMapper {
    BasicViewToViewEntityMapper basicViewToViewEntityMapper = Mappers.getMapper(BasicViewToViewEntityMapper.class);

    /**
     * Map a Group to a GroupEntity.
     *
     * @param group: the GroupEntity to map
     * @return the mapped GroupEntity
     */
//    @Override
    // Unmapped target property: "fields". Mapping from Collection element "BasicView views" to "ViewEntity views".
    // => can't map them / not needed, but also can't ignore=true them properly because part of collection
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mappings({
            @Mapping(source = "parentGroup", target = "parentGroup", qualifiedByName = "ParentMapper"),
            @Mapping(source = "childGroups", target = "childGroups", qualifiedByName = "ChildMapper"),
            // in fact, group still gets mapped properly throughout the tree because group -> field mapping already sets field.group?
            // TODO: investigate this further
            @Mapping(source = "views", target = "views", qualifiedByName = "BasicViewToViewEntityWithoutGroupMapper")
    })
    GroupEntity convert(@Nullable Group group);

    @Named("ParentMapper")
    @InheritConfiguration(name = "convert")
    @Mapping(target = "parentGroup", qualifiedByName = "ParentMapper")
    // Set inside GroupToGroupEntityMapperDecorator. TODO: better way?
    @Mapping(target = "childGroups", ignore = true)
    @BeanMapping(ignoreUnmappedSourceProperties = {"childGroups", "views"})
    GroupEntity mapParents(@Nullable Group group);

    @Named("ChildMapper")
    @InheritConfiguration(name = "convert")
    @Mapping(target = "childGroups", qualifiedByName = "ChildMapper")
    // TODO: parent seems to be set anyways? => analyse this
    @Mapping(target = "parentGroup", ignore = true)
    @BeanMapping(ignoreUnmappedSourceProperties = {"views", "parentGroup"})
    GroupEntity mapChildren(@Nullable Group group);
}
