package eu.fischerserver.controlpage.controlpagebackend.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import lombok.Builder;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Builder
public record Group(
        int id,

        @JsonManagedReference(value = "parent-group")
        @ToString.Exclude
        List<Group> childGroups,

        @JsonBackReference(value = "parent-group")
        Group parentGroup,

        String name,

        @JsonManagedReference(value = "viewGroup")
        @ToString.Exclude
        List<BasicView> views
) {
    /*
        Because they are stored in a database two Groups with the same id are considered equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
