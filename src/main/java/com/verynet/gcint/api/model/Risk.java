package com.verynet.gcint.api.model;

import com.verynet.gcint.api.util.enums.RiskImpacts;
import com.verynet.gcint.api.util.enums.RiskLevels;
import com.verynet.gcint.api.util.enums.RiskProbabilities;
import com.verynet.gcint.api.util.enums.RiskProcedences;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * Created by day on 26/09/2016.
 */
@Entity
@Table
public class Risk {
    private Integer id;
    private Object description;
    private Object generator;
    private Object assets;
    private Object cause;
    private Object consequence;
    private String probability;
    private String impact;
    private String level;
    private Integer procedence;
    private Boolean includeInReport;
    private Objective objective;
    private List<Activity> activities;

    public Risk() {
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
    @Column(name = "description")
    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "generator")
    public Object getGenerator() {
        return generator;
    }

    public void setGenerator(Object generator) {
        this.generator = generator;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "assets")
    public Object getAssets() {
        return assets;
    }

    public void setAssets(Object assets) {
        this.assets = assets;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "cause")
    public Object getCause() {
        return cause;
    }

    public void setCause(Object cause) {
        this.cause = cause;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "consequence")
    public Object getConsequence() {
        return consequence;
    }

    public void setConsequence(Object consequence) {
        this.consequence = consequence;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public Integer getProcedence() {
        return procedence;
    }

    public void setProcedence(Integer procedence) {
        this.procedence = procedence;
    }

    @Column(name = "include_in_report")
    public Boolean getIncludeInReport() {
        return includeInReport;
    }

    public void setIncludeInReport(Boolean includeInReport) {
        this.includeInReport = includeInReport;
    }

    @ManyToOne(targetEntity = Objective.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "objective_id")
    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    @OneToMany(targetEntity = Activity.class, mappedBy = "risk", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public void setLevel(String level) {
        if (probability != null && impact != null) {
            level = (probability.equals(RiskProbabilities.Bajo.toString()) && (impact.equals(RiskImpacts.Bajo.toString()) ||
                    impact.equals(RiskImpacts.Tolerable.toString()))) || (probability.equals(RiskProbabilities.Tolerable.toString()) && (impact.equals(RiskImpacts.Bajo.toString()) ||
                    impact.equals(RiskImpacts.Tolerable.toString()))) || (probability.equals(RiskProbabilities.Mediano.toString()) && impact.equals(RiskImpacts.Bajo.toString())) ?
                    RiskLevels.Bajo.toString() : (impact.equals(RiskImpacts.Mediano.toString()) && (probability.equals(RiskProbabilities.Bajo.toString()) ||
                    probability.equals(RiskProbabilities.Tolerable.toString()))) || (probability.equals(RiskProbabilities.Mediano.toString()) && impact.equals(RiskImpacts.Tolerable.toString())) ||
                    (probability.equals(RiskProbabilities.Alto.toString()) && impact.equals(RiskImpacts.Bajo.toString())) ? RiskLevels.Moderado.toString() :
                    (probability.equals(RiskProbabilities.Extremo.toString()) && (impact.equals(RiskImpacts.Bajo.toString()) || impact.equals(RiskImpacts.Tolerable.toString()))) ||
                            (probability.equals(RiskProbabilities.Alto.toString()) && (impact.equals(RiskImpacts.Tolerable.toString()) || impact.equals(RiskImpacts.Mediano.toString()))) ||
                            (probability.equals(RiskProbabilities.Mediano.toString()) && impact.equals(RiskImpacts.Mediano.toString())) ||
                            (probability.equals(RiskProbabilities.Tolerable.toString()) && impact.equals(RiskImpacts.Alto.toString())) ||
                            (probability.equals(RiskProbabilities.Bajo.toString()) && (impact.equals(RiskImpacts.Alto.toString()) ||
                                    impact.equals(RiskImpacts.Extremo.toString()))) ? RiskLevels.Alto.toString() : RiskLevels.Extremo.toString();
        }
        this.level = level;

    }

    public String getLevel() {
        return level;
    }

    public String Procedence() {
        switch (procedence) {
            case 1:
                return RiskProcedences.Interno.toString();
            case 2:
                return RiskProcedences.Externo.toString();
            default:
                return "";
        }
    }
}
