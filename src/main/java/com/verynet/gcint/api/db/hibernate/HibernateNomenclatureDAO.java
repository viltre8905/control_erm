package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.NomenclatureDAO;
import com.verynet.gcint.api.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 22/08/2016.
 */
public class HibernateNomenclatureDAO extends HibernateGeneralDAO implements NomenclatureDAO {
    public HibernateNomenclatureDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Ocupation saveOcupation(Ocupation ocupation) {
        currentSession().saveOrUpdate(ocupation);
        return ocupation;
    }

    @Override
    public Ocupation getOcupation(Integer id) {
        return (Ocupation) currentSession().get(Ocupation.class, id);
    }


    @Override
    public Ocupation getOcupation(String name) {
        List<Ocupation> ocupations = currentSession().createCriteria(Ocupation.class).add(Restrictions.eq("name", name)).list();
        return ocupations.size() > 0 ? ocupations.get(0) : null;
    }

    @Override
    public List<Ocupation> getAllOcupations() {
        return currentSession().createCriteria(Ocupation.class).list();
    }

    @Override
    public boolean deleteOcupation(Integer id) {
        Ocupation ocupation = getOcupation(id);
        if (ocupation != null) {
            currentSession().delete(ocupation);
            return true;
        }
        return false;
    }

    @Override
    public DocumentType saveDocumentType(DocumentType documentType) {
        currentSession().saveOrUpdate(documentType);
        return documentType;
    }

    @Override
    public DocumentType getDocumentType(Integer id) {
        return (DocumentType) currentSession().get(DocumentType.class, id);
    }


    @Override
    public DocumentType getDocumentType(String name) {
        List<DocumentType> documentTypes = currentSession().createCriteria(DocumentType.class).add(Restrictions.eq("name", name)).list();
        return documentTypes.size() > 0 ? documentTypes.get(0) : null;
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return currentSession().createCriteria(DocumentType.class).list();
    }

    @Override
    public boolean deleteDocumentType(Integer id) {
        DocumentType documentType = getDocumentType(id);
        if (documentType != null) {
            currentSession().delete(documentType);
            return true;
        }
        return false;
    }

    @Override
    public DocumentProcedence saveDocumentProcedence(DocumentProcedence documentProcedence) {
        currentSession().saveOrUpdate(documentProcedence);
        return documentProcedence;
    }

    @Override
    public DocumentProcedence getDocumentProcedence(Integer id) {
        return (DocumentProcedence) currentSession().get(DocumentProcedence.class, id);
    }


    @Override
    public DocumentProcedence getDocumentProcedence(String name) {
        List<DocumentProcedence> documentProcedences = currentSession().createCriteria(DocumentProcedence.class).add(Restrictions.eq("name", name)).list();
        return documentProcedences.size() > 0 ? documentProcedences.get(0) : null;
    }

    @Override
    public List<DocumentProcedence> getAllDocumentProcedences() {
        return currentSession().createCriteria(DocumentProcedence.class).list();
    }

    @Override
    public boolean deleteDocumentProcedence(Integer id) {
        DocumentProcedence documentProcedence = getDocumentProcedence(id);
        if (documentProcedence != null) {
            currentSession().delete(documentProcedence);
            return true;
        }
        return false;
    }

    @Override
    public ControlAction saveControlAction(ControlAction controlAction) {
        currentSession().saveOrUpdate(controlAction);
        return controlAction;
    }

    @Override
    public ControlAction getControlAction(Integer id) {
        return (ControlAction) currentSession().get(ControlAction.class, id);
    }

    @Override
    public ControlAction getControlAction(String name) {
        List<ControlAction> controlActions = currentSession().createCriteria(ControlAction.class).add(Restrictions.eq("name", name)).list();
        return controlActions.size() > 0 ? controlActions.get(0) : null;
    }

    @Override
    public List<ControlAction> getAllControlActions() {
        return currentSession().createCriteria(ControlAction.class).list();
    }

    @Override
    public boolean deleteControlAction(Integer id) {
        ControlAction controlAction = getControlAction(id);
        if (controlAction != null) {
            currentSession().delete(controlAction);
            return true;
        }
        return false;
    }

    @Override
    public Nefficacy saveNefficacy(Nefficacy nefficacy) {
        currentSession().saveOrUpdate(nefficacy);
        return nefficacy;
    }

    @Override
    public Nefficacy getNefficacy(Integer id) {
        return (Nefficacy) currentSession().get(Nefficacy.class, id);
    }

    @Override
    public Nefficacy getNefficacy(String name) {
        List<Nefficacy> nefficacies = currentSession().createCriteria(Nefficacy.class)
                .add(Restrictions.eq("name", name)).list();
        return nefficacies.size() > 0 ? nefficacies.get(0) : null;
    }

    @Override
    public Nefficacy getNefficacyByPercent(Double percent) {
        List<Nefficacy> nefficacies = currentSession().createCriteria(Nefficacy.class)
                .add(Restrictions.le("percent", percent)).addOrder(Order.desc("percent")).list();
        if (nefficacies.size() == 0) {
            nefficacies = currentSession().createCriteria(Nefficacy.class).addOrder(Order.asc("percent")).list();
        }
        return nefficacies.size() > 0 ? nefficacies.get(0) : null;
    }

    @Override
    public List<Nefficacy> getAllNefficacies() {
        return currentSession().createCriteria(Nefficacy.class).list();
    }

    @Override
    public boolean deleteNefficacy(Integer id) {
        Nefficacy nefficacy = getNefficacy(id);
        if (nefficacy != null) {
            currentSession().delete(nefficacy);
            return true;
        }
        return false;
    }

    @Override
    public Component getComponent(Integer id) {
        return (Component) currentSession().get(Component.class, id);
    }

    @Override
    public Component getComponent(String code) {
        List<Component> components = currentSession().createCriteria(Component.class)
                .add(Restrictions.eq("code", code)).list();
        return components.size() > 0 ? components.get(0) : null;
    }

    @Override
    public List<Component> getAllComponents() {
        return currentSession().createCriteria(Component.class).list();
    }
}
