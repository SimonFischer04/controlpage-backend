package eu.fischerserver.controlpage.controlpagebackend.model.domain;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.Action;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.StyledText;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import lombok.Builder;
import lombok.ToString;

import java.util.Objects;

@Builder
public record Field(
        int id,

        @ToString.Exclude
        FullView view,

        Action action,

        StyledText title,
        String description,

        Image background,

        int rowspan,
        int colspan
) {
    /*
        Because they are stored in a database two Fields with the same id are considered equals
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return id == field.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
