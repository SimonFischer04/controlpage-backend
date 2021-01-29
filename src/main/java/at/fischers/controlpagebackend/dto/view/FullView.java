package at.fischers.controlpagebackend.dto.view;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.ViewEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullView extends BasicView {
    private Collection<Collection<Field>> fields;

    private void addFields(Collection<Field> list) {
        fields.add(list);
        list.forEach(field -> field.setView(this));
    }

    public FullView(ViewEntity view) {
        super(view);
        fields = new ArrayList<>();

        TreeMap<Integer, TreeMap<Integer, Field>> map = new TreeMap<>();
        view.getFields().forEach(fieldEntity -> {
            if (!map.containsKey(fieldEntity.getYPos())) {
                map.put(fieldEntity.getYPos(), new TreeMap<>());
            }
            map.get(fieldEntity.getYPos()).put(fieldEntity.getXPos(), new Field(fieldEntity));
        });
        map.forEach((key, val) -> addFields(val.values()));
    }
}
