package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 22/09/2016.
 */
@Entity
@Table(name = "n_efficacy")
public class Nefficacy {
    private Integer id;
    private String name;
    private Double percent;

    public Nefficacy() {
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

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
