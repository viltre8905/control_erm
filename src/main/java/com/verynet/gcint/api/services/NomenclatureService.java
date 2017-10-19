package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.NomenclatureDAO;
import com.verynet.gcint.api.model.*;

import java.util.List;

/**
 * Created by day on 22/08/2016.
 */
public interface NomenclatureService {
    public void setNomenclatureDAO(NomenclatureDAO dao);

    public Ocupation saveOcupation(Ocupation ocupation);

    public Ocupation getOcupation(Integer id);

    public List<Ocupation> getAllOcupations();

    public boolean deleteOcupation(Integer id);

    public DocumentType saveDocumentType(DocumentType documentType);

    public DocumentType getDocumentType(Integer id);

    public boolean deleteDocumentType(Integer id);

    public List<DocumentType> getAllDocumentTypes();

    public DocumentProcedence saveDocumentProcedence(DocumentProcedence documentProcedence);

    public DocumentProcedence getDocumentProcedence(Integer id);

    public List<DocumentProcedence> getAllDocumentProcedences();

    public boolean deleteDocumentProcedence(Integer id);

    public ControlAction saveControlAction(ControlAction controlAction);

    public ControlAction getControlAction(Integer id);

    public List<ControlAction> getAllControlActions();

    public boolean deleteControlAction(Integer id);

    public Nefficacy saveNefficacy(Nefficacy nefficacy);

    public Nefficacy getNefficacy(Integer id);

    public Nefficacy getNefficacyByPercent(Double percent);

    public List<Nefficacy> getAllNefficacies();

    public boolean deleteNefficacy(Integer id);

    public Component getComponent(Integer id);

    public Component getComponent(String code);

    public List<Component> getAllComponents();
}
