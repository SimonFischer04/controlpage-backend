package at.fischers.controlpagebackend.entity.action;

import at.fischers.controlpagebackend.enums.ViewActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "view_action")
@PrimaryKeyJoinColumn(name = "action_id")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewActionEntity extends ActionEntity{
    private ViewActionType type;
    private int viewId;
}
