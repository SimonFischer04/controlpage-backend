package eu.fischerserver.controlpage.controlpagebackend.model.entity.action;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "desktop_automation_action")
@PrimaryKeyJoinColumn(name = "action_id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DesktopAutomationActionEntity extends ActionEntity {
    private String functionName;

    @SuppressWarnings("unused")
    @Builder
    public DesktopAutomationActionEntity(int id, FieldEntity field, RunPolicy runPolicy, String functionName) {
        super(id, field, runPolicy);
        this.functionName = functionName;
    }
}
