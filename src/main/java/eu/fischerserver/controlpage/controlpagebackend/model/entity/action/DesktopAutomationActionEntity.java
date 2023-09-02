package eu.fischerserver.controlpage.controlpagebackend.model.entity.action;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

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
}
