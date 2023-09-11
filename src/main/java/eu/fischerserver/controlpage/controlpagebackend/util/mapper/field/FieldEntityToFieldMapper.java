package eu.fischerserver.controlpage.controlpagebackend.util.mapper.field;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.util.mapper.action.ActionEntityToActionMapper;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Mapper(config = MapperSpringConfig.class, uses = ActionEntityToActionMapper.class)
public interface FieldEntityToFieldMapper extends Converter<FieldEntity, Field> {

    /**
     * Map a {@link FieldEntity} to a {@link Field}
     *
     * @param fieldEntity: the {@link FieldEntity} to map
     * @return the mapped {@link Field}
     */
    @Override
//    @Mappings({
//            // TODO: re-add this for DTO
////            @Mapping(source = "background.id", target = "backgroundId")
//    })
    @Mappings({
            // TODO: handle better?
            @Mapping(target = "action", qualifiedByName = "ActionEntityToActionWithoutBackReferencesMapper")
    })
    @BeanMapping(ignoreUnmappedSourceProperties = {
            // TODO: test rm
            "XPos",
            "YPos"
    })
    Field convert(@Nullable FieldEntity fieldEntity);

    @InheritConfiguration(name = "convert")
    @Mappings({
            @Mapping(target = "view", source = "view", ignore = true),
    })
    @Named("FieldEntityToFieldWithoutBackReferencesMapper")
    Field mapFieldEntityToFieldWithoutBackReferences(@Nullable FieldEntity fieldEntity);

    default List<List<Field>> mapFieldEntityListToFieldMatrix(List<FieldEntity> fieldEntities) {
        if (fieldEntities == null)
            return null;

        /*
            Mapping Fields:
            FieldEntities are stored using x- and y- position -> map to 2D array
         */
        // 2D array of field
        List<List<Field>> fields = new ArrayList<>();
        // Map <yPos, Map<xPos, Field>>
        TreeMap<Integer, TreeMap<Integer, Field>> map = new TreeMap<>();
        fieldEntities.forEach(fieldEntity -> {
            if (!map.containsKey(fieldEntity.getYPos())) {
                map.put(fieldEntity.getYPos(), new TreeMap<>());
            }
            map.get(fieldEntity.getYPos()).put(fieldEntity.getXPos(), mapFieldEntityToFieldWithoutBackReferences(fieldEntity));
        });
        map.forEach((key, list) -> fields.add(new ArrayList<>(list.values())));
        return fields;
    }
}
