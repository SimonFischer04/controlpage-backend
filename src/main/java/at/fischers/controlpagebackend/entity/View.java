package at.fischers.controlpagebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    private Group group;

    @ToString.Exclude
    @OneToMany(mappedBy = "view", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Field> fields;
}
