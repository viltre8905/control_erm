package com.verynet.gcint.api.model;

import com.verynet.gcint.api.util.enums.RiskLevels;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 05/09/2016.
 */
@Entity
@Table
public class Objective {
    private Integer id;
    private String objective;
    private Object description;
    private List<Risk> risks;
    private GeneralProcess process;

    public Objective() {
        risks = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "description")
    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    @ManyToOne(targetEntity = GeneralProcess.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "process_id")
    public GeneralProcess getProcess() {
        return process;
    }

    public void setProcess(GeneralProcess process) {
        this.process = process;
    }

    @OneToMany(targetEntity = Risk.class, mappedBy = "objective", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Risk> getRisks() {
        return risks;
    }

    public void setRisks(List<Risk> risks) {
        this.risks = risks;
    }

    public boolean isMoreAffected(Objective objective) {
        int low1 = 0, moderated1 = 0, high1 = 0, extreme1 = 0;
        int low2 = 0, moderated2 = 0, high2 = 0, extreme2 = 0;
        for (Risk risk : risks) {
            if (risk.getLevel().equals(RiskLevels.Bajo.toString())) {
                low1++;
            } else if (risk.getLevel().equals(RiskLevels.Bajo.toString())) {
                moderated1++;
            } else if (risk.getLevel().equals(RiskLevels.Bajo.toString())) {
                high1++;
            } else {
                extreme1++;
            }
        }
        for (Risk risk : objective.getRisks()) {
            if (risk.getLevel().equals(RiskLevels.Bajo.toString())) {
                low2++;
            } else if (risk.getLevel().equals(RiskLevels.Bajo.toString())) {
                moderated2++;
            } else if (risk.getLevel().equals(RiskLevels.Bajo.toString())) {
                high2++;
            } else {
                extreme2++;
            }
        }
        if (extreme1 > extreme2) {
            return true;
        } else if (extreme1 < extreme2) {
            return false;
        } else if (high1 > high2) {
            return true;
        } else if (high1 < high2) {
            return false;
        } else if (moderated1 > moderated2) {
            return true;
        } else if (moderated1 < moderated2) {
            return false;
        } else if (low1 > low2) {
            return true;
        } else if (low1 < low2) {
            return false;
        }
        return true;
    }
}
