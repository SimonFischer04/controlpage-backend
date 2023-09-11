package eu.fischerserver.controlpage.controlpagebackend.util.mapper.image;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ImageEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
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
