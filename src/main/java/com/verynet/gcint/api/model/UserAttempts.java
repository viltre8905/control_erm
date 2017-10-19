package com.verynet.gcint.api.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by day on 05/04/2017.
 */
@Entity
@Table(name = "user_attempts")
public class UserAttempts {
    private Integer id;
    private User user;
    private Integer attempts;
    private Date lastModified;

    public UserAttempts() {
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
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    @Column(name = "last_modified")
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
