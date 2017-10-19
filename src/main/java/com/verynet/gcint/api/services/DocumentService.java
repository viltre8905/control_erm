package com.verynet.gcint.api.services;

import com.verynet.gcint.api.annotation.Logging;
import com.verynet.gcint.api.db.DocumentDAO;
import com.verynet.gcint.api.model.Document;
import com.verynet.gcint.api.model.DocumentMetadata;

import java.util.List;

/**
 * Created by day on 18/09/2016.
 */
public interface DocumentService {

    public void setDocumentDAO(DocumentDAO dao);

    public Document saveDocument(Document document);

    public Document getDocument(Integer id);

    public List<Document> getAllDocuments();

    public List<DocumentMetadata> getAllDocumentMetadatas();

    public DocumentMetadata saveDocumentMetadata(DocumentMetadata documentMetadata);

    public DocumentMetadata getDocumentMetadata(String id);

    public boolean deleteDocumentMetadata(String id);
}
