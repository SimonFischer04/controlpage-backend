package at.fischers.controlpagebackend.util.mapper.group;

import at.fischers.controlpagebackend.config.MapperSpringConfig;
import at.fischers.controlpagebackend.model.domain.Group;
import at.fischers.controlpagebackend.model.entity.GroupEntity;
import at.fischers.controlpagebackend.util.mapper.view.ViewEntityToBasicViewMapper;
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
//            @Mapping(target = "parentGroup", expression = "java(groupEntity == null ? null : convert(groupEntity.getParentGroup()))"),
            @Mapping(target = "parentGroup", source = "parentGroup", qualifiedByName = "GroupEntityToGroup"),
            @Mapping(target = "childGroups", source = "childGroups", qualifiedByName = "GroupEntityToGroup"),
            @Mapping(target = "views", source = "views", qualifiedByName = "ViewEntityToBasicView")
    })
    @Named("GroupEntityToGroup")
    Group convert(@Nullable GroupEntity groupEntity);
}
