package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.controller.ViewController;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class)
public interface FullViewToFullViewDTOMapper extends Converter<FullView, ViewController.FullViewDTO> {
    @Override
    @Mappings({
            @Mapping(target = "groupId", source = "group.id")
    })
    ViewController.FullViewDTO convert(@Nullable FullView fullView);
}
