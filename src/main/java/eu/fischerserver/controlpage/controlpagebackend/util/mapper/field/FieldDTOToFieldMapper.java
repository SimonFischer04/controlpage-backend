package eu.fischerserver.controlpage.controlpagebackend.util.mapper.field;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.controller.ViewController;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
import eu.fischerserver.controlpage.controlpagebackend.service.ImageService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapperSpringConfig.class)
public abstract class FieldDTOToFieldMapper implements Converter<ViewController.FieldDTO, Field> {
    @Override
    @Mappings({
            @Mapping(target = "background", source = "backgroundId", qualifiedByName = "backgroundMapper"),
            @Mapping(target = "view", source = "viewId", ignore = true)
    })
    public abstract Field convert(@Nullable ViewController.FieldDTO fieldDTO);

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy // See: https://github.com/mapstruct/mapstruct-spring-extensions/issues/93
    private ImageService imageService;

    @Named("backgroundMapper")
    public Image mapBackground(Integer backgroundId) {
        return imageService.findById(backgroundId);
    }

    // TODO: why can't this be done automagically?
    public List<List<Field>> mapFieldMatrixToFieldDTOMatrix(List<List<ViewController.FieldDTO>> fieldDtoMatrix) {
        List<List<Field>> matrix = new ArrayList<>();
        fieldDtoMatrix.forEach(dtoRow -> {
            List<Field> row = new ArrayList<>();
            dtoRow.forEach(field -> row.add(convert(field)));
            matrix.add(row);
        });
        return matrix;
    }
}
