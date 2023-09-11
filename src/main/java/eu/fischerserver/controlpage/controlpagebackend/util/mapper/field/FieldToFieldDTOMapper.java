package eu.fischerserver.controlpage.controlpagebackend.util.mapper.field;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.controller.ViewController;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapperSpringConfig.class)
public interface FieldToFieldDTOMapper extends Converter<Field, ViewController.FieldDTO> {
    @Override
    @Mappings({
            @Mapping(target = "backgroundId", source = "background.id"),
            @Mapping(target = "viewId", source = "view.id")
    })
    ViewController.FieldDTO convert(@Nullable Field field);

    // TODO: why can't this be done automagically?
    default List<List<ViewController.FieldDTO>> mapFieldMatrixToFieldDTOMatrix(List<List<Field>> fieldMatrix) {
        List<List<ViewController.FieldDTO>> dtoMatrix = new ArrayList<>();
        fieldMatrix.forEach(row -> {
            List<ViewController.FieldDTO> dtoRow = new ArrayList<>();
            row.forEach(field -> dtoRow.add(convert(field)));
            dtoMatrix.add(dtoRow);
        });
        return dtoMatrix;
    }
}
