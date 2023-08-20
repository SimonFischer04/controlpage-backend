package eu.fischerserver.controlpage.controlpagebackend.util.mapper.group;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class)
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
            @Mapping(source = "parentGroup", target = "parentGroup", qualifiedByName = "GroupToGroupEntity"),
            @Mapping(source = "childGroups", target = "childGroups", qualifiedByName = "GroupToGroupEntity"),
    })
    @Named("GroupToGroupEntity")
    GroupEntity convert(@Nullable Group group);
}
