package com.verynet.gcint.api.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Created by day on 21/08/2016.
 */
@Entity
@Table(name = "n_control_action")
public class ControlAction {
    private Integer id;
    private String name;
    private List<ActionControlInform> informs;

    public ControlAction() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = ActionControlInform.class, mappedBy = "controlAction", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<ActionControlInform> getInforms() {
        return informs;
    }

    public void setInforms(List<ActionControlInform> informs) {
        this.informs = informs;
    }
}
