package com.verynet.gcint.api.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 21/08/2016.
 */
@javax.persistence.Entity
@Table(name = "entity_data")
public class EntityData {
    private Integer id;
    private String name;
    private String address;
    private String webAddress;
    private Object vision;
    private Object mission;
    private Object pathLogo;
    private EntityData parent;
    private List<EntityData> entities;
    private List<User> users;
    private List<GeneralProcess> processes;
    private List<Reunion> reunions;
    private List<Theme>themes;

    public EntityData() {
        entities = new ArrayList<>();
        users = new ArrayList<>();
        processes = new ArrayList<>();
        reunions = new ArrayList<>();
        themes=new ArrayList<>();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "web_address")
    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column
    public Object getVision() {
        return vision;
    }

    public void setVision(Object vision) {
        this.vision = vision;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column
    public Object getMission() {
        return mission;
    }

    public void setMission(Object mission) {
        this.mission = mission;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "path_logo")
    public Object getPathLogo() {
        return pathLogo;
    }

    public void setPathLogo(Object pathLogo) {
        this.pathLogo = pathLogo;
    }

    @ManyToOne(targetEntity = EntityData.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_id")
    public EntityData getParent() {
        return parent;
    }

    public void setParent(EntityData parent) {
        this.parent = parent;
    }

    @OneToMany(targetEntity = EntityData.class, mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<EntityData> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityData> entities) {
        this.entities = entities;
    }

    @OneToMany(targetEntity = User.class, mappedBy = "entity", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @OneToMany(targetEntity = GeneralProcess.class, mappedBy = "entity", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<GeneralProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(List<GeneralProcess> processes) {
        this.processes = processes;
    }

    @OneToMany(targetEntity = Reunion.class, mappedBy = "entity", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<Reunion> getReunions() {
        return reunions;
    }

    public void setReunions(List<Reunion> reunions) {
        this.reunions = reunions;
    }

    @OneToMany(targetEntity = Theme.class, mappedBy = "entity", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public void simpleClone(EntityData target) {
        this.id = target.getId();
        this.name = target.getName();
        this.address = target.getAddress();
        this.webAddress = target.getWebAddress();
        this.vision = target.getVision();
        this.mission = target.getMission();
        this.pathLogo = target.getPathLogo();
    }

    public String toHtmlNestable() {
        String result = String.format("<li class='dd-item' data-id='%s'><div class='dd-handle'>%s</div>", id, name);
        if (entities.size() > 0) {
            result += " <ol class='dd-list'>";
        }
        for (int i = 0; i < entities.size(); i++) {
            result += entities.get(i).toHtmlNestable();
        }
        if (entities.size() > 0) {
            result += "</ol>";
        }
        result += "</li>";
        return result;
    }
}
