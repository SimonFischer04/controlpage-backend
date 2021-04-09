package at.fischers.controlpagebackend.util.mapper.groupmapper;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.GroupEntity;

import java.util.ArrayList;
import java.util.List;

public class GroupMapperDTOToEntity {
    /*
        Note: i know this is kind of code duplication to the GroupMapperEntityToDTO, but building a "framework" (superclass,...)
        would create more overhead / is over-engineered
     */

    /**
     * This functions maps the basic attributes of a Group to a GroupEntity (like name, id,..)
     * (NOTE: parentGroup and childGroups will still be null)
     *
     * @param group: the Group to map
     * @return the mapped GroupEntity
     */
    private static GroupEntity basicMapping(Group group) {
        return GroupEntity.builder()
                .id(group.getId())
                .parentGroup(null)
                .childGroups(null)
                // NOTE: this must be set by the view mapper (otherwise would create an infinity recursion)
                .view(null)
                .name(group.getName())
                .build();
    }

    /**
     * recursively maps all the children of the Group to GroupEntity
     *
     * @param group:        the Group to map
     * @param parentEntity: used by the recursive algorithm to set the "parentEntity" at the child (initially null)
     * @return the ROOT! GroupEntity
     */
    private static GroupEntity mapChildren(Group group, GroupEntity parentEntity) {
        GroupEntity g = basicMapping(group);
        List<GroupEntity> children = new ArrayList<>();
        if (group.getChildGroups() != null) {
            group.getChildGroups().forEach(child -> children.add(mapChildren(child, g)));
        }
        g.setChildGroups(children);
        g.setParentGroup(parentEntity);
        return g;
    }

    /**
     * Searches a GroupEntity in the "Group-tree" using its id.
     *
     * @param startGroupEntity: the GroupEntity from where to start the search (initially root group)
     * @param id:               the id of the GroupEntity to search for
     * @return the found Group or null if not found
     */
    private static GroupEntity searchGroup(GroupEntity startGroupEntity, int id) {
        if (startGroupEntity.getId() == id) {
            return startGroupEntity;
        }
        if (startGroupEntity.getChildGroups() == null)
            return null;

        for (GroupEntity group : startGroupEntity.getChildGroups()) {
            GroupEntity found = searchGroup(group, id);
            if (found != null && found.getId() == id) {
                return found;
            }
        }
        return null;
    }

    /**
     * Map a Group to a GroupEntity.
     *
     * @param group: the Group to map
     * @return the mapped GroupEntity
     */
    public static GroupEntity mapDTOToEntity(Group group) {
        if (group == null)
            return null;

        // First go all the way up to the root group to get everything mapped.
        Group head = group;
        while (head.getParentGroup() != null) {
            head = head.getParentGroup();
        }

        // mapping all the children starting from the root group
        GroupEntity rootGroup = mapChildren(head, null);

        // finding right group again
        return searchGroup(rootGroup, group.getId());
    }
}