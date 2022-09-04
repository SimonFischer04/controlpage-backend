package at.fischers.controlpagebackend.util.mapper;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.service.ImageService;

public class FieldMapper {
    /**
     * Map a {@link FieldEntity} to a {@link Field}
     *
     * @param fieldEntity: the {@link FieldEntity} to map
     * @return the mapped {@link Field}
     */
    public static Field mapEntityToDTO(FieldEntity fieldEntity) {
        Field f = Field.builder()
                .id(fieldEntity.getId())
                .title(fieldEntity.getTitle())
                .backgroundId(getBackgroundId(fieldEntity))
                .action(ActionMapper.mapEntityToDTO(fieldEntity.getAction()))
                .rowspan(fieldEntity.getRowspan())
                .colspan(fieldEntity.getColspan())
                .build();
        if (f.getAction() != null)
            f.getAction().setField(f);
        return f;
    }

    private static int getBackgroundId(FieldEntity fieldEntity) {
        if (fieldEntity.getBackground() == null)
            return -1;
        return fieldEntity.getBackground().getId();
    }

    /**
     * Map a {@link Field} to a {@link FieldEntity}
     *
     * @param field: the {@link Field} to map
     * @return the mapped {@link FieldEntity}
     */
    public static FieldEntity mapDTOToEntity(ImageService imageService, Field field) {
        FieldEntity f = FieldEntity.builder()
                .id(field.getId())
                .title(field.getTitle())
                .background(ImageMapper.mapDTOToEntity(getImage(imageService, field)))
                .action(ActionMapper.mapDTOToEntity(field.getAction()))
                .rowspan(field.getRowspan())
                .colspan(field.getColspan())
                .build();
        if (f.getAction() != null)
            f.getAction().setField(f);
        return f;
    }

    private static Image getImage(ImageService imageService, Field field) {
        if (field.getBackgroundId() < 0)
            return null;

        return imageService.findById(field.getBackgroundId());
    }
}
