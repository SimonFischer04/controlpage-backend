package eu.fischerserver.controlpage.controlpagebackend.model.entity.action;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RestType;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "rest_action")
@PrimaryKeyJoinColumn(name = "action_id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RestActionEntity extends ActionEntity{
    private RestType restType;
    private String url;
    private String body;

    public RestActionEntity(int id, FieldEntity field, RunPolicy runPolicy, RestType restType, String url, String body) {
        super(id, field, runPolicy);
        this.restType = restType;
        this.url = url;
        this.body = body;
    }
}
