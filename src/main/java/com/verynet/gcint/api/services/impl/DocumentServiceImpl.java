package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.DocumentDAO;
import com.verynet.gcint.api.model.Document;
import com.verynet.gcint.api.model.DocumentMetadata;
import com.verynet.gcint.api.services.DocumentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 18/09/2016.
 */
@Transactional
public class DocumentServiceImpl implements DocumentService {
    private DocumentDAO dao;

    @Override
    public void setDocumentDAO(DocumentDAO dao) {
        this.dao = dao;
    }

    @Override
    public Document saveDocument(Document document) {
        return dao.saveDocument(document);
    }

    @Override
    @Transactional(readOnly = true)
    public Document getDocument(Integer id) {
        return dao.getDocument(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> getAllDocuments() {
        return dao.getAllDocuments();
    }

    @Override
    public List<DocumentMetadata> getAllDocumentMetadatas() {
        return dao.getAllDocumentMetadatas();
    }

    @Override
    public DocumentMetadata saveDocumentMetadata(DocumentMetadata documentMetadata) {
        return dao.saveDocumentMetadata(documentMetadata);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentMetadata getDocumentMetadata(String id) {
        return dao.getDocumentMetadata(id);
    }

    @Override
    public boolean deleteDocumentMetadata(String id) {
        return dao.deleteDocumentMetadata(id);
    }
}
