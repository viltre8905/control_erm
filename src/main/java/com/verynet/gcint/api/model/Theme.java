package com.verynet.gcint.api.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * Created by day on 19/02/2017.
 */
@Entity
@Table(name = "theme")
public class Theme {
    private Integer id;
    private Integer no;
    private Object theme;
    private EntityData entity;
    private List<DiscussionDate> discussionDates;

    public Theme() {
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
    @Column(name = "theme")
    public Object getTheme() {
        return theme;
    }

    public void setTheme(Object theme) {
        this.theme = theme;
    }

    @ManyToOne(targetEntity = EntityData.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entity_id")
    public EntityData getEntity() {
        return entity;
    }

    public void setEntity(EntityData entity) {
        this.entity = entity;
    }

    @OneToMany(targetEntity = DiscussionDate.class, mappedBy = "theme", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<DiscussionDate> getDiscussionDates() {
        return discussionDates;
    }

    public void setDiscussionDates(List<DiscussionDate> discussionDates) {
        this.discussionDates = discussionDates;
    }
}
