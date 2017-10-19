package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.DocumentDAO;
import com.verynet.gcint.api.model.Document;
import com.verynet.gcint.api.model.DocumentMetadata;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by day on 18/09/2016.
 */
public class HibernateDocumentDAO extends HibernateGeneralDAO implements DocumentDAO {
    public HibernateDocumentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Document saveDocument(Document document) {
        currentSession().saveOrUpdate(document);
        return document;
    }

    @Override
    public Document getDocument(Integer id) {
        return (Document) currentSession().get(Document.class, id);
    }

    @Override
    public List<Document> getAllDocuments() {
        return currentSession().createCriteria(Document.class).list();
    }

    @Override
    public List<DocumentMetadata> getAllDocumentMetadatas() {
        return currentSession().createCriteria(DocumentMetadata.class).list();
    }

    @Override
    public DocumentMetadata saveDocumentMetadata(DocumentMetadata documentMetadata) {
        currentSession().saveOrUpdate(documentMetadata);
        return documentMetadata;
    }

    @Override
    public DocumentMetadata getDocumentMetadata(String id) {
        return (DocumentMetadata) currentSession().get(DocumentMetadata.class, id);
    }

    @Override
    public boolean deleteDocumentMetadata(String id) {
        DocumentMetadata documentMetadata = getDocumentMetadata(id);
        if (documentMetadata != null) {
            currentSession().delete(documentMetadata);
            return true;
        }
        return false;
    }
}
