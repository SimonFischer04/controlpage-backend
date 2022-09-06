package at.fischers.controlpagebackend.entity.action;

import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.enums.RunPolicy;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
