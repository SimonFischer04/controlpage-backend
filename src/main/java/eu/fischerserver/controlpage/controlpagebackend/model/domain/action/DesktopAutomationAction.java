package eu.fischerserver.controlpage.controlpagebackend.model.domain.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DesktopAutomationAction extends Action {
    private String functionName;
}
