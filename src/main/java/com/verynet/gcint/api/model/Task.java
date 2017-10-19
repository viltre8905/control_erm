package com.verynet.gcint.api.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by day on 15/02/2017.
 */
@Entity
@Table
public class Task {
    private Integer id;
    private TaskName taskName;
    private Date lastExecution;
    private boolean stopped;
    private Integer frequency;

    public Task() {
        this.frequency = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = TaskName.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "task_name_id")
    public TaskName getTaskName() {
        return taskName;
    }

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    @Column(name = "last_execution")
    public Date getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}
