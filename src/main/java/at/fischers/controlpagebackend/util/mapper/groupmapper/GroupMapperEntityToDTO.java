package at.fischers.controlpagebackend.util.mapper.groupmapper;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.GroupEntity;

import java.util.ArrayList;
import java.util.List;

public class GroupMapperEntityToDTO {
    /*
        Note: i know this is kind of code duplication to the GroupMapperDTOToEntity, but building a "framework" (superclass,...)
        would create more overhead / is over-engineered
     */

    /**
     * This functions maps the basic attributes of a GroupEntity to a Group (like name, id,..)
     * (NOTE: parentGroup and childGroups will still be null)
     *
     * @param groupEntity: the GroupEntity to map
     * @return the mapped Group
     */
    private static Group basicMapping(GroupEntity groupEntity) {
        return Group.builder()
                .id(groupEntity.getId())
                .parentGroup(null)
                .childGroups(null)
                // NOTE: this must be set by the view mapper (otherwise would create an infinity recursion)
                .views(new ArrayList<>())
                .name(groupEntity.getName())
                .build();
    }

    /**
     * recursively maps all the children of the GroupEntity to Group
     *
     * @param groupEntity: the GroupEntity to map
     * @param parent:      used by the recursive algorithm to set the "parent" at the child (initially null)
     * @return the ROOT! group
     */
    private static Group mapChildren(GroupEntity groupEntity, Group parent) {
        Group g = basicMapping(groupEntity);
        List<Group> children = new ArrayList<>();
        if (groupEntity.getChildGroups() != null) {
            groupEntity.getChildGroups().forEach(child -> children.add(mapChildren(child, g)));
        }
        g.setChildGroups(children);
        g.setParentGroup(parent);
        return g;
    }

    /**
     * Searches a Group in the "Group-tree" using its id.
     *
     * @param startGroup: the Group from where to start the search (initially root group)
     * @param id:         the id of the Group to search for
     * @return the found Group or null if not found
     */
    private static Group searchGroup(Group startGroup, int id) {
        if (startGroup.getId() == id) {
            return startGroup;
        }
        if (startGroup.getChildGroups() == null)
            return null;

        for (Group group : startGroup.getChildGroups()) {
            Group found = searchGroup(group, id);
            if (found != null && found.getId() == id) {
                return found;
            }
        }
        return null;
    }

    /**
     * Map a {@link GroupEntity} to a {@link Group}.
     *
     * @param groupEntity: the {@link GroupEntity} to map
     * @return the mapped {@link Group} or null if input groupEntity is null
     */
    public static Group mapEntityToDTO(GroupEntity groupEntity) {
        if (groupEntity == null)
            return null;

        // First go all the way up to the root group to get everything mapped.
        GroupEntity head = groupEntity;
        while (head.getParentGroup() != null) {
            head = head.getParentGroup();
        }

        // mapping all the children starting from the root group
        Group rootGroup = mapChildren(head, null);

        // finding right group again
        return searchGroup(rootGroup, groupEntity.getId());
    }
}
