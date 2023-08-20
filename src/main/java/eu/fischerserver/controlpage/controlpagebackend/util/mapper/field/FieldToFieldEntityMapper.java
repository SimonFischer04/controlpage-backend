package eu.fischerserver.controlpage.controlpagebackend.util.mapper.field;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(config = MapperSpringConfig.class)
public interface FieldToFieldEntityMapper extends Converter<Field, FieldEntity> {
    /**
     * Map a {@link Field} to a {@link FieldEntity}
     *
     * @param field: the {@link Field} to map
     * @return the mapped {@link FieldEntity}
     */
    @Override
    @Mappings({
            // TODO: test rm
            @Mapping(target = "xPos", ignore = true),
            @Mapping(target = "yPos", ignore = true)
    })
    FieldEntity convert(@Nullable Field field);

    @Mappings({
            // TODO: test rm
            @Mapping(target = "xPos", ignore = true),
            @Mapping(target = "yPos", ignore = true),
            @Mapping(target = "view", ignore = true)
    })
    @BeanMapping(ignoreUnmappedSourceProperties = "view")
    @Named("FieldToFieldEntityWithoutViewMapper")
    FieldEntity mapFieldToFieldEntityWithoutView(@Nullable Field field);

    default List<FieldEntity> mapFieldMatrixToFieldEntityList(List<List<Field>> fieldMatrix) {
        if (fieldMatrix == null)
            return null;

        List<FieldEntity> fields = new ArrayList<>();
        int y = 0;
        for (Collection<Field> row : fieldMatrix) {
            int x = 0;
            for (Field field : row) {
                FieldEntity fieldEntity = mapFieldToFieldEntityWithoutView(field);
                if (fieldEntity == null)
                    continue;

                fieldEntity.setYPos(y);
                fieldEntity.setXPos(x);
                fields.add(fieldEntity);
                x++;
            }
            y++;
        }
        return fields;
    }
}
