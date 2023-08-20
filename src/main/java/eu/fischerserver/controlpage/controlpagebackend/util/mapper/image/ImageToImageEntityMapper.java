package eu.fischerserver.controlpage.controlpagebackend.util.mapper.image;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ImageEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
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
