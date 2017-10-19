package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 04/09/2017.
 */
@Entity
@Table(name = "activity_process")
public class ActivityProcess extends GeneralProcess {
    private GeneralProcess parent;

    public ActivityProcess() {
    }

    @ManyToOne(targetEntity = GeneralProcess.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "process_parent")
    public GeneralProcess getParent() {
        return parent;
    }

    public void setParent(GeneralProcess parent) {
        this.parent = parent;
    }
}
