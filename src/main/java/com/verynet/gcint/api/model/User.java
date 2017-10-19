package com.verynet.gcint.api.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 25/07/2016.
 */
@Entity
@Table(name = "auth_user")
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private Timestamp dateJoined;
    private Timestamp lastLogin;
    private List<Role> roles;
    private Ocupation ocupation;
    private String identification;
    private Object pathPhoto;
    private List<GeneralProcess> processes;
    private EntityData entity;
    private List<Notification> notifications;

    public User() {
        notifications = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "accountNonLocked")
    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Column(name = "date_joined")
    public Timestamp getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Timestamp dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Column(name = "last_login")
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @ManyToOne(targetEntity = Ocupation.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ocupation_id")
    public Ocupation getOcupation() {
        return ocupation;
    }

    public void setOcupation(Ocupation ocupation) {
        this.ocupation = ocupation;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "path_photo")
    public Object getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(Object pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

    @ManyToMany(mappedBy = "members", targetEntity = GeneralProcess.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<GeneralProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(List<GeneralProcess> processes) {
        this.processes = processes;
    }

    @ManyToOne(targetEntity = EntityData.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entity_id")
    public EntityData getEntity() {
        return entity;
    }

    public void setEntity(EntityData entity) {
        this.entity = entity;
    }

    @OneToMany(targetEntity = Notification.class, mappedBy = "target", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void deleteRole(String value) {
        boolean flag = true;
        for (int i = 0; i < roles.size() && flag; i++) {
            if (roles.get(i).getValue().equals(value)) {
                flag = false;
                roles.remove(i);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            if (((User) o).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRole(String value) {
        for (Role role : roles) {
            if (role.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOnlyRole(String value) {
        return hasRole(value) && roles.size() == 1;
    }
}
