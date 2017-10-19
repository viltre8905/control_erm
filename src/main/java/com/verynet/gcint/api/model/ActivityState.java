package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 17/09/2016.
 */
@Entity
@Table(name = "activity_state")
public class ActivityState {
    private Integer id;
    private String name;

    public ActivityState() {
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
}
