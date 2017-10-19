package com.verynet.gcint.api.model;

import com.verynet.gcint.api.util.TimestampUtil;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by day on 09/09/2016.
 */
@Entity
@Table
public class Activity {
    private Integer id;
    private Object activityDescription;
    private User responsible;
    private User executor;
    private Date accomplishDate;
    private Object observation;
    private ActivityState activityState;
    private Evidence evidence;
    private GeneralProcess process;
    private Component component;
    private Date initDate;
    private Risk risk;
    private Deficiency deficiency;

    public Activity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "activity_description")
    public Object getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(Object activityDescription) {
        this.activityDescription = activityDescription;
    }

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "responsible")
    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "executor")
    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    @Column(name = "accomplish_date")
    public Date getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(Date accomplishDate) {
        this.accomplishDate = accomplishDate;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "observation")
    public Object getObservation() {
        return observation;
    }

    public void setObservation(Object observation) {
        this.observation = observation;
    }


    @ManyToOne(targetEntity = ActivityState.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "activity_state_id")
    public ActivityState getActivityState() {
        return activityState;
    }

    public void setActivityState(ActivityState activityState) {
        this.activityState = activityState;
    }

    @ManyToOne(targetEntity = Evidence.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "evidence_id")
    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public String toShortAccomplishDate() {
        return TimestampUtil.toShortDate(accomplishDate);
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

    @Column(name = "init_date")
    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    @ManyToOne(targetEntity = Risk.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "risk_id")
    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    @ManyToOne(targetEntity = Deficiency.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "deficiency_id")
    public Deficiency getDeficiency() {
        return deficiency;
    }

    public void setDeficiency(Deficiency deficiency) {
        this.deficiency = deficiency;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Activity) {
            if (((Activity) o).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
