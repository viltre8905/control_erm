package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 09/09/2016.
 */
@Entity
@Table(name = "negative_answer")
public class NegativeAnswer extends Answer {
    private Deficiency deficiency;

    public NegativeAnswer() {
    }

    @ManyToOne(targetEntity = Deficiency.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,  CascadeType.REMOVE})
    @JoinColumn(name = "deficiency_id")
    public Deficiency getDeficiency() {
        return deficiency;
    }

    public void setDeficiency(Deficiency deficiency) {
        this.deficiency = deficiency;
    }
}
