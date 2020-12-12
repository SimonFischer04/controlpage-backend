package at.fischers.controlpagebackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "view_group")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "parentGroup")
    private List<GroupEntity> childGroups;

    @ManyToOne
    private GroupEntity parentGroup;

    private String name;
}
