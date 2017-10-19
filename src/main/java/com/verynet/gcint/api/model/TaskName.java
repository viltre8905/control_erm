package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 15/02/2017.
 */
@Entity
@Table(name = "n_task_name")
public class TaskName {
    private Integer id;
    private String name;
    private String description;

    public TaskName() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
