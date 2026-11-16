package eu.fischerserver.controlpage.controlpagebackend.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import lombok.Builder;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Builder(toBuilder = true)
public record Group(
        int id,

        @ToString.Exclude
        List<Group> childGroups,

        // back reference: not serialized (Jackson 3 does not support managed/back references on records)
        @JsonIgnore
        Group parentGroup,

        String name,

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
