package at.fischers.controlpagebackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "view")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne
    private GroupEntity group;

    @OneToMany(mappedBy = "view", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FieldEntity> fields;
}
