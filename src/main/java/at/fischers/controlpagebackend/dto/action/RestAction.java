package at.fischers.controlpagebackend.dto.action;

import at.fischers.controlpagebackend.entity.action.RestActionEntity;
import at.fischers.controlpagebackend.enums.ActionType;
import at.fischers.controlpagebackend.enums.RestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestAction extends Action{
    private RestType restType;
    private String url;
    private String body;

    public RestAction(RestActionEntity entity){
        super(entity);
        setActionType(ActionType.REST);
        restType = entity.getType();
        url = entity.getUrl();
        body = entity.getBody();
    }
}
