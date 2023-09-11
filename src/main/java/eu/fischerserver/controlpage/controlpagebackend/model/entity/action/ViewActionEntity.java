package eu.fischerserver.controlpage.controlpagebackend.model.entity.action;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.ViewActionType;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "view_action")
@PrimaryKeyJoinColumn(name = "action_id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ViewActionEntity extends ActionEntity {
    private ViewActionType viewActionType;
    private int viewId;

    @Builder(builderMethodName = "viewActionEntityBuilder")
    public ViewActionEntity(int id, FieldEntity field, RunPolicy runPolicy, ViewActionType viewActionType, int viewId) {
        super(id, field, runPolicy);
        this.viewActionType = viewActionType;
        this.viewId = viewId;
    }
}
