package com.verynet.gcint.api.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by day on 09/09/2016.
 */
@Entity
@Table
public class Question {
    private Integer id;
    private String code;
    private String title;
    private Object description;
    private Boolean procedure;
    private List<Answer> answers;
    private Date start;

    public Question() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "description")
    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Boolean getProcedure() {
        return procedure;
    }

    public void setProcedure(Boolean procedure) {
        this.procedure = procedure;
    }

    @OneToMany(targetEntity = Answer.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "question_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Answer> getAnswers() {
        return answers;
    }

    @Column(name = "start_date")
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getAnswerType(Integer processId) {
        Integer answerType = 0;
        if (answers != null) {
            boolean flag = true;
            Iterator iterator = answers.iterator();
            while (flag && iterator.hasNext()) {
                Answer answer = (Answer) iterator.next();
                if (answer.getProcess().getId().equals(processId)) {
                    if (answer instanceof AffirmativeAnswer) {
                        answerType = 1;
                        flag = false;
                    } else if (answer instanceof NegativeAnswer) {
                        answerType = 2;
                        flag = false;
                    } else if (answer instanceof RejectAnswer) {
                        answerType = 3;
                        flag = false;
                    }
                }
            }
        }
        return answerType;
    }

    public Answer answerFromProcess(Integer processId) {
        Answer result = null;
        if (answers != null) {
            boolean flag = true;
            Iterator iterator = answers.iterator();
            while (flag && iterator.hasNext()) {
                Answer answer = (Answer) iterator.next();
                if (answer.getProcess().getId().equals(processId)) {
                    result = answer;
                    flag = false;
                }
            }
        }
        return result;
    }

    public NegativeAnswer negativeAnswer(Integer processId) {
        NegativeAnswer result = null;
        if (answers != null) {
            for (Answer answer : answers) {
                if (answer.getProcess().getId().equals(processId) && answer instanceof NegativeAnswer) {
                    result = (NegativeAnswer) answer;
                }
            }
        }
        return result;
    }

}
