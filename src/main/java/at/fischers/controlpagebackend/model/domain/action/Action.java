package at.fischers.controlpagebackend.model.domain.action;

import at.fischers.controlpagebackend.model.domain.Field;
import at.fischers.controlpagebackend.model.global.action.ActionType;
import at.fischers.controlpagebackend.model.global.action.RunPolicy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RestAction.class, name = ActionType.REST),
        @JsonSubTypes.Type(value = ViewAction.class, name = ActionType.VIEW),
        @JsonSubTypes.Type(value = DesktopAutomationAction.class, name = ActionType.DESKTOP_AUTOMATION)
})
public abstract class Action {
    private int id;

    @ToString.Exclude
    @JsonIgnore
    private Field field;

    private RunPolicy runPolicy;

    /*
        Because they are stored in a database two Actions with the same id are considered equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return id == action.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
