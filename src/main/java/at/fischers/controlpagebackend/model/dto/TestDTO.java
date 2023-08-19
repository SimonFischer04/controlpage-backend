package at.fischers.controlpagebackend.model.dto;

import at.fischers.controlpagebackend.model.domain.Field;
import at.fischers.controlpagebackend.model.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDTO {
    //    @JsonIgnore
    private int id;
    private String name;
    private Group group;

    private List<List<Field>> fields;
}
