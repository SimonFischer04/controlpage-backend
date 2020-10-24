package at.fischers.controlpagebackend.entity;

import at.fischers.controlpagebackend.entity.action.Action;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JsonBackReference
    @ToString.Exclude
    private View view;

    @OneToOne
    @JsonManagedReference
    private Action action;

    private String background;

    private int rowspan;
    private int colspan;
}
