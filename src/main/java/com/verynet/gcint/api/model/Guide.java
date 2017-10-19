package com.verynet.gcint.api.model;

import com.verynet.gcint.api.util.OrderUtil;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 08/09/2016.
 */
@Entity
@Table
public class Guide {
    private Integer id;
    private String name;
    private Object description;
    private Component component;
    private GeneralProcess process;
    private List<Aspect> aspects;

    public Guide() {
        aspects = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "description")
    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    @ManyToOne(targetEntity = Component.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "component_id")
    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @ManyToOne(targetEntity = GeneralProcess.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "process_id")
    public GeneralProcess getProcess() {
        return process;
    }

    public void setProcess(GeneralProcess process) {
        this.process = process;
    }

    @OneToMany(targetEntity = Aspect.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "guide_id")
    public List<Aspect> getAspects() {
        return aspects;
    }

    public void setAspects(List<Aspect> aspects) {
        this.aspects = aspects;
    }

    public List<Aspect> orderedAspects() {
        quickSort(aspects, 0, aspects.size() - 1);
        return aspects;
    }

    public static void quickSort(List<Aspect> vector, int left, int right) {
        Aspect pivot = vector.get(left);
        int i = left;
        int j = right;
        Aspect auxInterchange;
        while (i < j) {
            while (vector.get(i).getName().compareTo(pivot.getName()) <= 0 && i < j) {
                i++;
            }
            while (vector.get(j).getName().compareTo(pivot.getName()) > 0) {
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
