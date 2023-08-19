package at.fischers.controlpagebackend.util.mapper.view;

import at.fischers.controlpagebackend.config.MapperSpringConfig;
import at.fischers.controlpagebackend.model.domain.Field;
import at.fischers.controlpagebackend.model.domain.view.BasicView;
import at.fischers.controlpagebackend.model.domain.view.FullView;
import at.fischers.controlpagebackend.model.entity.FieldEntity;
import at.fischers.controlpagebackend.model.entity.ViewEntity;
import at.fischers.controlpagebackend.util.mapper.field.FieldToFieldEntityMapper;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(config = MapperSpringConfig.class, uses = FieldToFieldEntityMapper.class)
public interface FullViewToViewEntityMapper extends Converter<FullView, ViewEntity> {
    /**
     * Map a {@link FullView} to {@link ViewEntity}
     *
     * @param fullView: the {@link FullView} to map
     * @return the mapped {@link ViewEntity}
     */
    @Override
    ViewEntity convert(@Nullable FullView fullView);
}
