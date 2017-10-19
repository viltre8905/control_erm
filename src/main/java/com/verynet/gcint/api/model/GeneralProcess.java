package com.verynet.gcint.api.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 21/08/2016.
 */
@Entity
@Table(name = "general_process")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class GeneralProcess {
    private Integer id;
    private String name;
    private String description;
    private List<Objective> objectives;
    private User responsible;
    private List<User> members;
    private EntityData entity;

    public GeneralProcess() {
        members = new ArrayList<>();
        objectives=new ArrayList<>();
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

    @OneToMany(targetEntity = Objective.class, mappedBy = "process", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "responsible_id")
    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    @ManyToMany(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "proccess_user", joinColumns = @JoinColumn(name = "proccess_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @ManyToOne(targetEntity = EntityData.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entity_id")
    public EntityData getEntity() {
        return entity;
    }

    public void setEntity(EntityData entity) {
        this.entity = entity;
    }

    public Integer processType() {
        return this instanceof Process ? 1 : 2;
    }
}
