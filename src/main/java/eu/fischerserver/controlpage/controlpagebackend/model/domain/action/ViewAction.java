package eu.fischerserver.controlpage.controlpagebackend.model.domain.action;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.ViewActionType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewAction extends Action {
    private ViewActionType viewActionType;
    private int viewId;

    @SuppressWarnings("unused")
    @Builder
    public ViewAction(int id, Field field, RunPolicy runPolicy, ViewActionType viewActionType, int viewId) {
        super(id, field, runPolicy);
        this.viewActionType = viewActionType;
        this.viewId = viewId;
    }
}
