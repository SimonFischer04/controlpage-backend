package eu.fischerserver.controlpage.controlpagebackend.model.domain.action;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
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
