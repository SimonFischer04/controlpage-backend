package at.fischers.controlpagebackend.entity.action;

import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.enums.RunPolicy;
import at.fischers.controlpagebackend.enums.ViewActionType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "view_action")
@PrimaryKeyJoinColumn(name = "action_id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ViewActionEntity extends ActionEntity {
    private ViewActionType type;
    private int viewId;

    @SuppressWarnings("unused")
    @Builder
    public ViewActionEntity(int id, FieldEntity field, RunPolicy runPolicy, ViewActionType type, int viewId) {
        super(id, field, runPolicy);
        this.type = type;
        this.viewId = viewId;
    }
}
