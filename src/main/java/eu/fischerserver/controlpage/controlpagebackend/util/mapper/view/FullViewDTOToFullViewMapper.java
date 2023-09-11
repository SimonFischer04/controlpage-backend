package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.controller.ViewController;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.field.FieldDTOToFieldMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class, uses = FieldDTOToFieldMapper.class)
public interface FullViewDTOToFullViewMapper extends Converter<ViewController.FullViewDTO, FullView> {
    @Override
    @Mappings({
            // TODO: implement db fetching
            @Mapping(target = "group", source = "groupId", ignore = true)
    })
    FullView convert(@Nullable ViewController.FullViewDTO fullViewDTO);
}
