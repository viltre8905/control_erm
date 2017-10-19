package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 21/08/2016.
 */
@Entity
@Table(name = "n_document_type")
public class DocumentType {
    private Integer id;
    private String name;

    public DocumentType() {
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
}
