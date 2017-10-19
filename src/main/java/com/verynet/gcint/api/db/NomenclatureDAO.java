package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.*;

import java.util.List;

/**
 * Created by day on 22/08/2016.
 */
public interface NomenclatureDAO {
    public Ocupation saveOcupation(Ocupation ocupation);

    public Ocupation getOcupation(Integer id);

    public Ocupation getOcupation(String name);

    public List<Ocupation> getAllOcupations();

    public boolean deleteOcupation(Integer id);

    public DocumentType saveDocumentType(DocumentType documentType);

    public DocumentType getDocumentType(Integer id);

    public DocumentType getDocumentType(String name);

    public List<DocumentType> getAllDocumentTypes();

    public boolean deleteDocumentType(Integer id);

    public DocumentProcedence saveDocumentProcedence(DocumentProcedence documentProcedence);

    public DocumentProcedence getDocumentProcedence(Integer id);

    public DocumentProcedence getDocumentProcedence(String name);

    public List<DocumentProcedence> getAllDocumentProcedences();

    public boolean deleteDocumentProcedence(Integer id);

    public ControlAction saveControlAction(ControlAction controlAction);

    public ControlAction getControlAction(Integer id);

    public ControlAction getControlAction(String name);

    public List<ControlAction> getAllControlActions();

    public boolean deleteControlAction(Integer id);

    public Nefficacy saveNefficacy(Nefficacy nefficacy);

    public Nefficacy getNefficacy(Integer id);

    public Nefficacy getNefficacy(String name);

    public Nefficacy getNefficacyByPercent(Double percent);

    public List<Nefficacy> getAllNefficacies();

    public boolean deleteNefficacy(Integer id);

    public Component getComponent(Integer id);

    public Component getComponent(String code);

    public List<Component> getAllComponents();

}
