package at.fischers.controlpagebackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
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
