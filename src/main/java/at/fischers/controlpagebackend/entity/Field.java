package at.fischers.controlpagebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //TODO: View
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private View view;

    //TODO: Action

    private String background;

    private int rowspan;
    private int colspan;
}
