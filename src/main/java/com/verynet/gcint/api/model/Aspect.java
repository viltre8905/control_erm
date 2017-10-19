package com.verynet.gcint.api.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 08/09/2016.
 */
@Entity
@Table
public class Aspect {
    private Integer id;
    private Integer no;
    private String name;
    private List<Question> questions;


    public Aspect() {
        questions = new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = Question.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "aspect_id")
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> orderedQuestions() {
        quickSort(questions, 0, questions.size() - 1);
        return questions;
    }

    public static void quickSort(List<Question> vector, int left, int right) {
        Question pivot = vector.get(left);
        int i = left;
        int j = right;
        Question auxInterchange;
        while (i < j) {
            while (vector.get(i).getCode().compareTo(pivot.getCode()) <= 0 && i < j) {
                i++;
            }
            while (vector.get(j).getCode().compareTo(pivot.getCode()) > 0) {
                j--;
            }
            if (i < j) {
                auxInterchange = vector.get(i);
                vector.set(i, vector.get(j));
                vector.set(j, auxInterchange);
            }
        }
        vector.set(left, vector.get(j));
        vector.set(j, pivot);
        if (left < j - 1) {
            quickSort(vector, left, j - 1);
        }
        if (j + 1 < right) {
            quickSort(vector, j + 1, right);
        }
    }
}
