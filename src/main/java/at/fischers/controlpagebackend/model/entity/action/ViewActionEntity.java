package at.fischers.controlpagebackend.model.entity.action;

import at.fischers.controlpagebackend.model.entity.FieldEntity;
import at.fischers.controlpagebackend.model.global.action.RunPolicy;
import at.fischers.controlpagebackend.model.global.action.ViewActionType;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

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

    @SuppressWarnings("unused")
    @Builder
    public ViewActionEntity(int id, FieldEntity field, RunPolicy runPolicy, ViewActionType viewActionType, int viewId) {
        super(id, field, runPolicy);
        this.viewActionType = viewActionType;
        this.viewId = viewId;
    }
}
