package at.fischers.controlpagebackend.entity.action;

import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.enums.RestType;
import at.fischers.controlpagebackend.enums.RunPolicy;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "rest_action")
@PrimaryKeyJoinColumn(name = "action_id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RestActionEntity extends ActionEntity{
    private RestType type;
    private String url;
    private String body;

    @SuppressWarnings("unused")
    @Builder
    public RestActionEntity(int id, FieldEntity field, RunPolicy runPolicy, RestType type, String url, String body) {
        super(id, field, runPolicy);
        this.type = type;
        this.url = url;
        this.body = body;
    }
}
