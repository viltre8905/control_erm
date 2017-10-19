package com.verynet.gcint.api.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by day on 28/07/2016.
 */
@Entity
@Table(name="auth_role")
public class Role {
    private Integer id;
    private String name;
    private String description;
    private String value;
    private List<User> users;

    public Role() {
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

    @ManyToMany(mappedBy = "roles", targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
