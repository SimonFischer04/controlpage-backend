package at.fischers.controlpagebackend.dto.view;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.ViewEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private Collection<Collection<Field>> fields = new ArrayList<>();

    private void addFields(Collection<Field> list) {
        fields.add(list);
        list.forEach(field -> field.setView(this));
    }

    public FullView(ViewEntity view) {
        super(view);

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
