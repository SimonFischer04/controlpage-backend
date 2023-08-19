package at.fischers.controlpagebackend.util.mapper.image;

import at.fischers.controlpagebackend.config.MapperSpringConfig;
import at.fischers.controlpagebackend.model.entity.ImageEntity;
import at.fischers.controlpagebackend.model.domain.Image;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class)
public interface ImageToImageEntityMapper extends Converter<Image, ImageEntity> {
    /**
     * Map a {@link Image} to an {@link ImageEntity}
     *
     * @param image: the {@link Image} to map
     * @return the mapped {@link ImageEntity} or null if input image is null
     */
    @Override
    ImageEntity convert(@Nullable Image image);
}
