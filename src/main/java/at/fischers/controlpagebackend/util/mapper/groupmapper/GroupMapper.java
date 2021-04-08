package at.fischers.controlpagebackend.util.mapper.groupmapper;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.GroupEntity;

public class GroupMapper {

    /**
     * Map a GroupEntity to a Group.
     *
     * @param group: the Group to map
     * @return the mapped GroupEntity
     */
    public static GroupEntity mapDTOToEntity(Group group) {
        return GroupMapperDTOToEntity.mapDTOToEntity(group);
    }

    /**
     * Map a GroupEntity to a Group.
     *
     * @param groupEntity: the GroupEntity to map
     * @return the mapped Group
     */
    public static Group mapEntityToDTO(GroupEntity groupEntity) {
        return GroupMapperEntityToDTO.mapEntityToDTO(groupEntity);
    }
}
