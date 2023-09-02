package eu.fischerserver.controlpage.controlpagebackend.model.domain.action;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RestType;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestAction extends Action {
    private RestType restType;
    private String url;
    private String body;

    public RestAction(int id, Field field, RunPolicy runPolicy, RestType restType, String url, String body) {
        super(id, field, runPolicy);
        this.restType = restType;
        this.url = url;
        this.body = body;
    }
}
