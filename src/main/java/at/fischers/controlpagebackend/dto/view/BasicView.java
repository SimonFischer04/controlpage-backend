package at.fischers.controlpagebackend.dto.view;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.util.mapper.ViewMapper;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(builderMethodName = "basicBuilder")
public class BasicView {
    private int id;
    private String name;

    @JsonBackReference(value = "viewGroup")
    private Group group;

    public BasicView(BasicView basicView) {
        id = basicView.getId();
        name = basicView.getName();
        group = basicView.getGroup();
    }

    public BasicView(ViewEntity view) {
        this(ViewMapper.mapEntityToBasicDTO(view));
    }

    /*
        Because they are stored in a database two Views with the same id are considered equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicView view = (BasicView) o;
        return id == view.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
