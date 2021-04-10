package at.fischers.controlpagebackend.util.mapper;

import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.entity.ImageEntity;

public class ImageMapper {
    /**
     * Map a {@link ImageEntity} to an {@link Image}
     *
     * @param imageEntity: the {@link ImageEntity} to map
     * @return the mapped {@link Image} or null if input imageEntity is null
     */
    public static Image mapEntityToDTO(ImageEntity imageEntity) {
        if (imageEntity == null)
            return null;

        return Image.builder()
                .id(imageEntity.getId())
                .name(imageEntity.getName())
                .type(imageEntity.getType())
                .imageData(imageEntity.getImageData())
                .build();
    }

    /**
     * Map a {@link Image} to an {@link ImageEntity}
     *
     * @param image: the {@link Image} to map
     * @return the mapped {@link ImageEntity} or null if input image is null
     */
    public static ImageEntity mapDTOToEntity(Image image) {
        if (image == null)
            return null;

        return ImageEntity.builder()
                .id(image.getId())
                .name(image.getName())
                .type(image.getType())
                .imageData(image.getImageData())
                .build();
    }
}
