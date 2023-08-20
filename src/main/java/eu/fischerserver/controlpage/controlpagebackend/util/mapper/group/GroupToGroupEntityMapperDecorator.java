package eu.fischerserver.controlpage.controlpagebackend.util.mapper.group;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupToGroupEntityMapperDecorator implements GroupToGroupEntityMapper, Converter<Group, GroupEntity> {
    @Autowired
    private GroupToGroupEntityMapper delegate;

    @Override
    public GroupEntity convert(@Nullable Group group) {
        var entity = delegate.convert(group);
        mapParentChildren(entity, entity);
        return entity;
    }

    private void mapParentChildren(GroupEntity start, GroupEntity groupEntity) {
        if(groupEntity == start){
            return;
        }

        List<GroupEntity> childGroups = groupEntity.getChildGroups();

        if (childGroups != null) {
            for (GroupEntity childGroup : childGroups) {
                childGroup.setParentGroup(groupEntity); // Set the parent for each child
                mapParentChildren(start, childGroup); // Recurse for child's children
            }
        }
    }
}
