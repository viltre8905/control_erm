package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 09/09/2016.
 */
@Entity
@Table(name = "affirmative_answer")
public class AffirmativeAnswer extends Answer  {
    private Evidence evidence;

    public AffirmativeAnswer() {
    }

    @ManyToOne(targetEntity = Evidence.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "evidence_id")
    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

}
