package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 21/08/2016.
 */
@Entity
@Table(name = "sub_process")
public class SubProcess extends GeneralProcess {
    private Process parent;

    public SubProcess() {
    }

    @ManyToOne(targetEntity = Process.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "process_parent")
    public Process getParent() {
        return parent;
    }

    public void setParent(Process parent) {
        this.parent = parent;
    }
}
