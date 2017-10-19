package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.NomenclatureDAO;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.services.NomenclatureService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 22/08/2016.
 */
@Transactional
public class NomenclatureServiceImpl implements NomenclatureService {
    private NomenclatureDAO dao;

    @Override
    public void setNomenclatureDAO(NomenclatureDAO dao) {
        this.dao = dao;
    }

    @Override
    public Ocupation saveOcupation(Ocupation ocupation) {
        Ocupation ocupationEntity;
        Ocupation anotherOcupation = dao.getOcupation(ocupation.getName());
        if (ocupation.getId() != null) {
            ocupationEntity = dao.getOcupation(ocupation.getId());
            if (anotherOcupation != null && !anotherOcupation.getId().equals(ocupation.getId())) {
                return null;
            }
            ocupationEntity.setName(ocupation.getName());
        } else {
            if (anotherOcupation != null) {
                return null;
            }
            ocupationEntity = ocupation;
        }
        return dao.saveOcupation(ocupationEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Ocupation getOcupation(Integer id) {
        return dao.getOcupation(id);
    }

    @Override
    public List<Ocupation> getAllOcupations() {
        return dao.getAllOcupations();
    }

    @Override
    public boolean deleteOcupation(Integer id) {
        return dao.deleteOcupation(id);
    }

    @Override
    public DocumentType saveDocumentType(DocumentType documentType) {
        DocumentType documentTypeEntity;
        DocumentType anotherDocumentType = dao.getDocumentType(documentType.getName());
        if (documentType.getId() != null) {
            documentTypeEntity = dao.getDocumentType(documentType.getId());
            if (anotherDocumentType != null && !anotherDocumentType.getId().equals(documentType.getId())) {
                return null;
            }
            documentTypeEntity.setName(documentType.getName());
        } else {
            if (anotherDocumentType != null) {
                return null;
            }
            documentTypeEntity = documentType;
        }
        return dao.saveDocumentType(documentTypeEntity);

    }

    @Override
    @Transactional(readOnly = true)
    public DocumentType getDocumentType(Integer id) {
        return dao.getDocumentType(id);
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return dao.getAllDocumentTypes();
    }

    @Override
    public boolean deleteDocumentType(Integer id) {
        return dao.deleteDocumentType(id);
    }

    @Override
    public DocumentProcedence saveDocumentProcedence(DocumentProcedence documentProcedence) {
        DocumentProcedence documentProcedenceEntity;
        DocumentProcedence anotherDocumentProcedence = dao.getDocumentProcedence(documentProcedence.getName());
        if (documentProcedence.getId() != null) {
            documentProcedenceEntity = dao.getDocumentProcedence(documentProcedence.getId());
            if (anotherDocumentProcedence != null && !anotherDocumentProcedence.getId().equals(documentProcedence.getId())) {
                return null;
            }
            documentProcedenceEntity.setName(documentProcedence.getName());
        } else {
            if (anotherDocumentProcedence != null) {
                return null;
            }
            documentProcedenceEntity = documentProcedence;
        }
        return dao.saveDocumentProcedence(documentProcedenceEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentProcedence getDocumentProcedence(Integer id) {
        return dao.getDocumentProcedence(id);
    }

    @Override
    public List<DocumentProcedence> getAllDocumentProcedences() {
        return dao.getAllDocumentProcedences();
    }

    @Override
    public boolean deleteDocumentProcedence(Integer id) {
        return dao.deleteDocumentProcedence(id);
    }

    @Override
    public ControlAction saveControlAction(ControlAction controlAction) {
        ControlAction controlActionEntity;
        ControlAction anotherControlAction = dao.getControlAction(controlAction.getName());
        if (controlAction.getId() != null) {
            controlActionEntity = dao.getControlAction(controlAction.getId());
            if (anotherControlAction != null && !anotherControlAction.getId().equals(controlAction.getId())) {
                return null;
            }
            controlActionEntity.setName(controlAction.getName());
        } else {
            if (anotherControlAction != null) {
                return null;
            }
            controlActionEntity = controlAction;
        }
        return dao.saveControlAction(controlActionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ControlAction getControlAction(Integer id) {
        return dao.getControlAction(id);
    }

    @Override
    public List<ControlAction> getAllControlActions() {
        return dao.getAllControlActions();
    }

    @Override
    public boolean deleteControlAction(Integer id) {
        return dao.deleteControlAction(id);
    }

    @Override
    public Nefficacy saveNefficacy(Nefficacy nefficacy) {
        Nefficacy nefficacyEntity;
        Nefficacy anotherNefficacy = dao.getNefficacy(nefficacy.getName());
        if (nefficacy.getId() != null) {
            nefficacyEntity = dao.getNefficacy(nefficacy.getId());
            if (anotherNefficacy != null && !anotherNefficacy.getId().equals(nefficacy.getId())) {
                return null;
            }
            nefficacyEntity.setName(nefficacy.getName());
            nefficacyEntity.setPercent(nefficacy.getPercent());
        } else {
            if (anotherNefficacy != null) {
                return null;
            }
            nefficacyEntity = nefficacy;
        }
        return dao.saveNefficacy(nefficacyEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Nefficacy getNefficacy(Integer id) {
        return dao.getNefficacy(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Nefficacy getNefficacyByPercent(Double percent) {
        return dao.getNefficacyByPercent(percent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nefficacy> getAllNefficacies() {
        return dao.getAllNefficacies();
    }

    @Override
    public boolean deleteNefficacy(Integer id) {
        return dao.deleteNefficacy(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Component getComponent(Integer id) {
        return dao.getComponent(id);
    }

    @Override
    public Component getComponent(String code) {
        return dao.getComponent(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Component> getAllComponents() {
        return dao.getAllComponents();
    }
}
