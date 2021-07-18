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
import java.util.stream.Collectors;

public class ViewMapper {
    /**
     * Map a {@link ViewEntity} to a {@link BasicView}.
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link BasicView}
     */
    public static BasicView mapEntityToBasicDTO(ViewEntity viewEntity) {
        Group group = GroupMapper.mapEntityToDTO(viewEntity.getGroup());
        BasicView basicView = BasicView.basicBuilder()
                .id(viewEntity.getId())
                .group(group)
                .name(viewEntity.getName())
                .build();
        group.getViews().add(basicView);

        return basicView;
    }

    /**
     * Map a {@link ViewEntity} to a {@link FullView}
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link FullView}
     */
    public static FullView mapEntityToFullDTO(ViewEntity viewEntity) {
        Group group = GroupMapper.mapEntityToDTO(viewEntity.getGroup());
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
            FieldEntities are stored using x- and y- postion -> map to 2D array
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
     * Map a {@link BasicView} or {@link FullView} to {@link ViewEntity}
     * (Doesn't check for any other views in the group of the view)
     * Also ads it to the view-list of the groupEntity
     *
     * @param view:        the {@link BasicView} or {@link FullView} to map
     * @param groupEntity: the already mapped {@link GroupEntity} where the {@link ViewEntity} should belong to
     * @return the mapped {@link ViewEntity}
     */
    private static ViewEntity mapDTOToEntitySingle(BasicView view, GroupEntity groupEntity) {
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

    /**
     * Map a {@link BasicView} or {@link FullView} to {@link ViewEntity}
     * Also maps all View's of the view's group!!!
     *
     * @param view: the {@link BasicView} or {@link FullView} to map
     * @return the mapped {@link ViewEntity}
     */
    public static ViewEntity mapDTOToEntity(BasicView view) {
        GroupEntity groupEntity = GroupMapper.mapDTOToEntity(view.getGroup());
        // check if any other views are in group of view that need to be mapped
        if (view.getGroup() != null && view.getGroup().getViews() != null)
            view.getGroup().getViews().forEach(viewEntity1 -> {
                // dont map the view supplied as parameter
                if (!viewEntity1.equals(view))
                    mapDTOToEntitySingle(viewEntity1, groupEntity);
            });

        return mapDTOToEntitySingle(view, groupEntity);
    }
}
