package com.verynet.gcint.api.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by day on 17/09/2016.
 */
@Entity
@Table
public class Evidence  {
    private Integer id;
    private Object description;
    private Document document;

    public Evidence() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Type(type = "com.verynet.gcint.api.util.hibernate.types.JacksonObjectType")
    @Column(name = "description")
    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    @ManyToOne(targetEntity = Document.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "document_id")
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
