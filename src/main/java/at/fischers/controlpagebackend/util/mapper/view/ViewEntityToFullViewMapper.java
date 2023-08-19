package at.fischers.controlpagebackend.util.mapper.view;

import at.fischers.controlpagebackend.config.MapperSpringConfig;
import at.fischers.controlpagebackend.model.domain.Field;
import at.fischers.controlpagebackend.model.domain.view.FullView;
import at.fischers.controlpagebackend.model.entity.FieldEntity;
import at.fischers.controlpagebackend.model.entity.ViewEntity;
import at.fischers.controlpagebackend.util.mapper.field.FieldEntityToFieldMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper(config = MapperSpringConfig.class, uses = FieldEntityToFieldMapper.class)
public interface ViewEntityToFullViewMapper extends Converter<ViewEntity, FullView> {
    /**
     * Map a {@link ViewEntity} to a {@link FullView}
     *
     * @param viewEntity: the {@link ViewEntity} to map
     * @return the mapped {@link FullView}
     */
    @Override
    @BeanMapping(resultType = FullView.class)
    FullView convert(@Nullable ViewEntity viewEntity);
}
