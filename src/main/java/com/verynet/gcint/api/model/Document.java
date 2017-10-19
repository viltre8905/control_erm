package com.verynet.gcint.api.model;

import javax.persistence.*;

/**
 * Created by day on 17/09/2016.
 */
@Entity
@Table
public class Document  {
    private Integer id;
    private String title;
    private DocumentType documentType;
    private DocumentProcedence documentProcedence;
    private DocumentMetadata documentMetadata;

    public Document() {
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

    @ManyToOne(targetEntity = DocumentType.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "document_type_id")
    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @ManyToOne(targetEntity = DocumentProcedence.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "document_procedence_id")
    public DocumentProcedence getDocumentProcedence() {
        return documentProcedence;
    }

    public void setDocumentProcedence(DocumentProcedence documentProcedence) {
        this.documentProcedence = documentProcedence;
    }

    @ManyToOne(targetEntity = DocumentMetadata.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "document_metadata_id")
    public DocumentMetadata getDocumentMetadata() {
        return documentMetadata;
    }

    public void setDocumentMetadata(DocumentMetadata documentMetadata) {
        this.documentMetadata = documentMetadata;
    }

}
