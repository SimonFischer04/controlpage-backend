package eu.fischerserver.controlpage.controlpagebackend.model.domain.view;

import com.fasterxml.jackson.annotation.JsonBackReference;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
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
