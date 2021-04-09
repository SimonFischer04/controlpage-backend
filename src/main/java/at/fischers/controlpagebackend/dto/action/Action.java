package at.fischers.controlpagebackend.dto.action;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.enums.ActionType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Action {
    private int id;

    @ToString.Exclude
    @JsonIgnore
    private Field field;

    private ActionType actionType;

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
