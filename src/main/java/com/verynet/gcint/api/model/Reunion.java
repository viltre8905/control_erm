package com.verynet.gcint.api.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by day on 29/09/2016.
 */
@Entity
@Table
public class Reunion {
    private Integer id;
    private String title;
    private Date start;
    private Date end;
    private String place;
    private String minuteUbication;
    private DocumentMetadata documentMetadata;
    private EntityData entity;

    public Reunion() {
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

    @Column(name = "start_date")
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Column(name = "end_date")
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Column(name = "minute_ubication")
    public String getMinuteUbication() {
        return minuteUbication;
    }

    public void setMinuteUbication(String minuteUbication) {
        this.minuteUbication = minuteUbication;
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

    public void simpleClone(Reunion target) {
        this.id = target.getId();
        this.title = target.getTitle();
        this.start = target.getStart();
        this.end = target.getEnd();
        this.place = target.getPlace();
        this.minuteUbication = target.getMinuteUbication();
        this.documentMetadata = target.getDocumentMetadata();
    }
}
