package eu.fischerserver.controlpage.controlpagebackend.model.domain.action;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.ViewActionType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ViewAction extends Action {
    private ViewActionType viewActionType;
    private int viewId;

    @Builder(builderMethodName = "viewActionBuilder")
    public ViewAction(int id, Field field, RunPolicy runPolicy, ViewActionType viewActionType, int viewId) {
        super(id, field, runPolicy);
        this.viewActionType = viewActionType;
        this.viewId = viewId;
    }
}
