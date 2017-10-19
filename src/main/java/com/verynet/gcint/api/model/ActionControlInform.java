package com.verynet.gcint.api.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * Created by day on 29/09/2016.
 */
@Entity
@Table(name = "action_control_inform")
public class ActionControlInform {
    private Integer id;
    private String title;
    private String ubication;
    private Object conclution;
    private ControlAction controlAction;
    private List<Deficiency> deficiencies;
    private DocumentMetadata documentMetadata;
    private EntityData entity;

    public ActionControlInform() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "conclution")
    public Object getConclution() {
        return conclution;
    }

    public void setConclution(Object conclution) {
        this.conclution = conclution;
    }

    @ManyToOne(targetEntity = ControlAction.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "control_action_id")
    public ControlAction getControlAction() {
        return controlAction;
    }

    public void setControlAction(ControlAction controlAction) {
        this.controlAction = controlAction;
    }

    @OneToMany(targetEntity = Deficiency.class, mappedBy = "actionControlInform", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<Deficiency> getDeficiencies() {
        return deficiencies;
    }

    public void setDeficiencies(List<Deficiency> deficiencies) {
        this.deficiencies = deficiencies;
    }

    @ManyToOne(targetEntity = DocumentMetadata.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "document_metadata_id")
    public DocumentMetadata getDocumentMetadata() {
        return documentMetadata;
    }

    public void setDocumentMetadata(DocumentMetadata documentMetadata) {
        this.documentMetadata = documentMetadata;
    }

    @ManyToOne(targetEntity = EntityData.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entity_id")
    public EntityData getEntity() {
        return entity;
    }

    public void setEntity(EntityData entity) {
        this.entity = entity;
    }
}
