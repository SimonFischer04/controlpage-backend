package at.fischers.controlpagebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "view_group")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /*@OneToOne(mappedBy = "group_id")
    private Group parentGroup;

    @OneToMany(mappedBy = "parent_group")
    private List<Group> childGroups;*/

    private String name;
}
