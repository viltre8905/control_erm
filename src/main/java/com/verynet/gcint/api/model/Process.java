package com.verynet.gcint.api.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by day on 21/08/2016.
 */
@Entity
@Table
public class Process extends GeneralProcess {
    private Object mission;
    private Object vision;

    public Process() {
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "mission")
    public Object getMission() {
        return mission;
    }

    public void setMission(Object mission) {
        this.mission = mission;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "vision")
    public Object getVision() {
        return vision;
    }

    public void setVision(Object vision) {
        this.vision = vision;
    }
}
