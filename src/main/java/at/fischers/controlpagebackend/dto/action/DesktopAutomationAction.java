package at.fischers.controlpagebackend.dto.action;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.enums.RunPolicy;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DesktopAutomationAction extends Action {
    private String functionName;

    @SuppressWarnings("unused")
    @Builder
    public DesktopAutomationAction(int id, Field field, RunPolicy runPolicy, String functionName) {
        super(id, field, runPolicy);
        this.functionName = functionName;
    }
}
