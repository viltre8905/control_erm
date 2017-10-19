package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 09/09/2016.
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Answer  {
    private Integer id;
    private GeneralProcess process;
    private Component component;

    public Answer() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = GeneralProcess.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "process_id")
    public GeneralProcess getProcess() {
        return process;
    }

    public void setProcess(GeneralProcess process) {
        this.process = process;
    }

    @ManyToOne(targetEntity = Component.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "component_id")
    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }


}
