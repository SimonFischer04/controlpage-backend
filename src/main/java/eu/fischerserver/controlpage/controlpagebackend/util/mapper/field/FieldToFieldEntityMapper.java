package eu.fischerserver.controlpage.controlpagebackend.util.mapper.field;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.action.ActionToActionEntityMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(config = MapperSpringConfig.class, uses = ActionToActionEntityMapper.class)
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
            @Mapping(target = "XPos", ignore = true),
            @Mapping(target = "YPos", ignore = true),
            // TODO: handle better?
            @Mapping(target = "action", qualifiedByName = "ActionToActionEntityWithoutBackReferencesMapper")
    })
    FieldEntity convert(@Nullable Field field);

    @InheritConfiguration(name = "convert")
    @Mappings({
            // TODO: test rm
            @Mapping(target = "view", source = "view", ignore = true),
    })
    @Named("FieldToFieldEntityWithoutBackReferencesMapper")
    FieldEntity mapFieldToFieldEntityWithoutBackReferences(@Nullable Field field);

    default List<FieldEntity> mapFieldMatrixToFieldEntityList(List<List<Field>> fieldMatrix) {
        if (fieldMatrix == null)
            return null;

        List<FieldEntity> fields = new ArrayList<>();
        int y = 0;
        for (Collection<Field> row : fieldMatrix) {
            int x = 0;
            for (Field field : row) {
                FieldEntity fieldEntity = mapFieldToFieldEntityWithoutBackReferences(field);
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
