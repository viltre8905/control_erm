package com.verynet.gcint.api.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 29/09/2016.
 */
@Entity
@Table
public class Deficiency {
    private Integer id;
    private Integer no;
    private Object body;
    private List<Activity> activities;
    private ActionControlInform actionControlInform;

    public Deficiency() {
        activities=new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "body")
    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @OneToMany(targetEntity = Activity.class, mappedBy = "deficiency", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @ManyToOne(targetEntity = ActionControlInform.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "action_control_inform_id")
    public ActionControlInform getActionControlInform() {
        return actionControlInform;
    }

    public void setActionControlInform(ActionControlInform actionControlInform) {
        this.actionControlInform = actionControlInform;
    }
}
