package at.fischers.controlpagebackend.model.domain.action;

import at.fischers.controlpagebackend.model.domain.Field;
import at.fischers.controlpagebackend.model.global.action.RestType;
import at.fischers.controlpagebackend.model.global.action.RunPolicy;
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
        super(id, field, runPolicy);
        this.restType = restType;
        this.url = url;
        this.body = body;
    }
}
