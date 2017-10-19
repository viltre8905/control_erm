package com.verynet.gcint.api.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by day on 09/09/2016.
 */
@Entity
@Table(name = "reject_answer")
public class RejectAnswer extends Answer {
    private Object description;

    public RejectAnswer() {
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "description")
    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

}
