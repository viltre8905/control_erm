package com.verynet.gcint.api.model;

import com.verynet.gcint.api.util.TimestampUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by day on 18/07/2017.
 */
@Entity
@Table(name = "discussion_date")
public class DiscussionDate {
    private Integer id;
    private User responsible;
    private Date discussionDate;
    private Theme theme;

    public DiscussionDate() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "responsible_id")
    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    @Column(name = "discussion_date")
    public Date getDiscussionDate() {
        return discussionDate;
    }

    public void setDiscussionDate(Date discussionDate) {
        this.discussionDate = discussionDate;
    }

    @ManyToOne(targetEntity = Theme.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "theme_id")
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String toShortDiscussionDate() {
        return TimestampUtil.toShortDate(discussionDate);
    }
}
