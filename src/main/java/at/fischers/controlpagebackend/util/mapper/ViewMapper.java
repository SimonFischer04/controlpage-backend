package at.fischers.controlpagebackend.util.mapper;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.util.mapper.groupmapper.GroupMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class ViewMapper {
    /**
     * Map a SINGLE! {@link ViewEntity} to a {@link BasicView}.
     * (Doesn't check for any other views in the group of the viewEntity)
     * Also ads it to the view-list of the group
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @param group:      the already mapped {@link Group} where the View should belong to
     * @return the mapped {@link BasicView}
     */
    private static BasicView mapEntityToBasicDTOSingle(ViewEntity viewEntity, Group group) {
        BasicView basicView = BasicView.basicBuilder()
                .id(viewEntity.getId())
                .group(group)
                .name(viewEntity.getName())
                .build();

        if (group != null) {
            group.getViews().add(basicView);
        }

        return basicView;
    }

    /**
     * Map a SINGLE! {@link ViewEntity} to a {@link FullView}.
     * (Doesn't check for any other views in the group of the viewEntity)
     * Also ads it to the view-list of the group
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @param group:      the already mapped {@link Group} where the View should belong to
     * @return the mapped {@link FullView}
     */
    private static FullView mapEntityToFullDTOSingle(ViewEntity viewEntity, Group group) {
        FullView fullView = FullView.fullBuilder()
                .id(viewEntity.getId())
                .group(group)
                .name(viewEntity.getName())
                .fields(null)
                .build();
        if (group != null)
            group.getViews().add(fullView);

        if (viewEntity.getFields() != null) {
        /*
            Mapping Fields:
            FieldEntities are stored using x- and y- position -> map to 2D array
         */
            // 2D array of field
            List<List<Field>> fields = new ArrayList<>();
            // Map <yPos, Map<xPos, Field>>
            TreeMap<Integer, TreeMap<Integer, Field>> map = new TreeMap<>();
            viewEntity.getFields().forEach(fieldEntity -> {
                if (!map.containsKey(fieldEntity.getYPos())) {
                    map.put(fieldEntity.getYPos(), new TreeMap<>());
                }
                map.get(fieldEntity.getYPos()).put(fieldEntity.getXPos(), FieldMapper.mapEntityToDTO(fieldEntity));
            });
            map.forEach((key, list) -> {
                fields.add(new ArrayList<>(list.values()));
                list.values().forEach(field -> field.setView(fullView));
            });
            fullView.setFields(fields);
        }

        return fullView;
    }

    /**
     * Checks if the viewEntity's group has more {@link ViewEntity}'s to map (children in group.views list)
     * DOES NOT MAP THE viewEntity (parameter)
     *
     * @param viewEntity:    the {@link ViewEntity} to check for potential more than one view per group
     * @param doFullMapping: whether or not a full mapping should be made
     *                       (if true -> do mapEntityToFullDTOSingle() and false -> mapEntityToBasicDTOSingle() is called)
     * @param group:         the already mapped {@link Group} where the views should belong to
     */
    private static void checkGroup(ViewEntity viewEntity, boolean doFullMapping, Group group) {
        // check if any other views are in group of viewEntity that need to be mapped
        if (viewEntity.getGroup() != null && viewEntity.getGroup().getViews() != null)
            viewEntity.getGroup().getViews().forEach(viewEntity1 -> {
                // dont map the viewEntity supplied as parameter
                if (!viewEntity1.equals(viewEntity))
                    if (doFullMapping)
                        mapEntityToFullDTOSingle(viewEntity1, group);
                    else
                        mapEntityToBasicDTOSingle(viewEntity1, group);
            });
    }

    /**
     * Map a {@link ViewEntity} to a {@link BasicView}.
     * Also maps all {@link ViewEntity}'s of the viewEntity's group!!!
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link BasicView}
     */
    public static BasicView mapEntityToBasicDTO(ViewEntity viewEntity) {
        Group group = GroupMapper.mapEntityToDTO(viewEntity.getGroup());
        checkGroup(viewEntity, false, group);
        return mapEntityToBasicDTOSingle(viewEntity, group);
    }

    /**
     * Map a {@link ViewEntity} to a {@link FullView}
     * Also maps all {@link ViewEntity}'s of the viewEntity's group!!!
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link FullView}
     */
    public static FullView mapEntityToFullDTO(ViewEntity viewEntity) {
        Group group = GroupMapper.mapEntityToDTO(viewEntity.getGroup());
        checkGroup(viewEntity, true, group);
        return mapEntityToFullDTOSingle(viewEntity, group);
    }

    // ----------------------------------------------------------------------------------------------

    /**
     * Map a {@link BasicView} or {@link FullView} to {@link ViewEntity}
     *
     * @param view: the {@link BasicView} or {@link FullView} to map
     * @return the mapped {@link ViewEntity}
     */
    public static ViewEntity mapDTOToEntity(BasicView view) {
        GroupEntity groupEntity = GroupMapper.mapDTOToEntity(view.getGroup());
        ViewEntity viewEntity = ViewEntity.builder()
                .id(view.getId())
                .group(groupEntity)
                .name(view.getName())
                .build();

        if (groupEntity != null)
            groupEntity.getViews().add(viewEntity);

        if (view instanceof FullView && ((FullView) view).getFields() != null) {
            List<FieldEntity> fields = new ArrayList<>();
            int y = 0;
            for (Collection<Field> row : ((FullView) view).getFields()) {
                int x = 0;
                for (Field field : row) {
                    FieldEntity fieldEntity = FieldMapper.mapDTOToEntity(field);

                    fieldEntity.setView(viewEntity);
                    fieldEntity.setYPos(y);
                    fieldEntity.setXPos(x);
                    fields.add(fieldEntity);
                    x++;
                }
                y++;
            }
            viewEntity.setFields(fields);
        }

        return viewEntity;
    }
}
