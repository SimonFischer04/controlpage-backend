package at.fischers.controlpagebackend.util.mapper.image;

import at.fischers.controlpagebackend.config.MapperSpringConfig;
import at.fischers.controlpagebackend.model.entity.ImageEntity;
import at.fischers.controlpagebackend.model.domain.Image;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class)
public interface ImageEntityToImageMapper extends Converter<ImageEntity, Image> {
    /**
     * Map a {@link ImageEntity} to an {@link Image}
     *
     * @param imageEntity: the {@link ImageEntity} to map
     * @return the mapped {@link Image} or null if input imageEntity is null
     */
    @Override
    Image convert(@Nullable ImageEntity imageEntity);
}
