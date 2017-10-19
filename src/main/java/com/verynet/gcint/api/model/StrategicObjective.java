package com.verynet.gcint.api.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by day on 24/02/2017.
 */
@Entity
@Table(name = "strategic_objective")
public class StrategicObjective {
    private Integer id;
    private String title;
    private Object objective;
    private EntityData entity;

    public StrategicObjective() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column
    public Object getObjective() {
        return objective;
    }

    public void setObjective(Object objective) {
        this.objective = objective;
    }

    @ManyToOne(targetEntity = EntityData.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entity_id")
    public EntityData getEntity() {
        return entity;
    }

    public void setEntity(EntityData entity) {
        this.entity = entity;
    }
}
