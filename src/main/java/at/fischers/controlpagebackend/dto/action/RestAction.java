package at.fischers.controlpagebackend.dto.action;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.enums.ActionType;
import at.fischers.controlpagebackend.enums.RestType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestAction extends Action {
    private RestType restType;
    private String url;
    private String body;

    @SuppressWarnings("unused")
    @Builder
    public RestAction(int id, Field field, RunPolicy runPolicy, RestType restType, String url, String body) {
        super(id, field, ActionType.REST, runPolicy);
        this.restType = restType;
        this.url = url;
        this.body = body;
    }
}
