package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 09/09/2016.
 */
@Entity
@Table(name = "n_component")
public class Component {
    private Integer id;
    private String name;
    private String code;

    public Component() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
