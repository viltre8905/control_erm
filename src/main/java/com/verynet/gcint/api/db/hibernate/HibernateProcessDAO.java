package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.ProcessDAO;
import com.verynet.gcint.api.model.GeneralProcess;
import com.verynet.gcint.api.model.Objective;
import com.verynet.gcint.api.model.Process;
import com.verynet.gcint.api.model.SubProcess;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 05/09/2016.
 */
public class HibernateProcessDAO extends HibernateGeneralDAO implements ProcessDAO {
    public HibernateProcessDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Process saveProcess(Process process) {
        currentSession().saveOrUpdate(process);
        return process;
    }

    @Override
    public Process getProcess(Integer id) {
        return (Process) currentSession().get(Process.class, id);
    }

    @Override
    public GeneralProcess getGeneralProcess(Integer id) {
        return (GeneralProcess) currentSession().get(GeneralProcess.class, id);
    }

    @Override
    public List<GeneralProcess> getAllGeneralProcess(Integer entityId) {
        return currentSession().createCriteria(GeneralProcess.class, "gp")
                .createAlias("gp.entity", "e").add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public Process getProcess(String name) {
        List<Process> process = currentSession().createCriteria(Process.class).add(Restrictions.eq("name", name)).list();
        return process.size() > 0 ? process.get(0) : null;
    }

    @Override
    public List<Process> getAllProcess() {
        return currentSession().createCriteria(Process.class).list();
    }

    @Override
    public List<Process> getAllProcess(Integer entityId) {
        return currentSession().createCriteria(Process.class).createAlias("entity", "e")
                .add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public List<GeneralProcess> getAllEntityProcess(Integer entityId) {
        return currentSession().createCriteria(GeneralProcess.class).createAlias("entity", "e")
                .add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public List<Process> getAllProcesses(String userName, boolean responsible) {
        if (responsible) {
            return currentSession().createCriteria(Process.class).createAlias("responsible", "r")
                    .add(Restrictions.eq("r.userName", userName)).list();
        } else {
            return currentSession().createCriteria(Process.class).createAlias("members", "m")
                    .add(Restrictions.eq("m.userName", userName)).list();
        }
    }

    @Override
    public List<SubProcess> getAllSubProcesses(String userName, boolean responsible) {
        if (responsible) {
            return currentSession().createCriteria(SubProcess.class).createAlias("responsible", "r")
                    .add(Restrictions.eq("r.userName", userName)).list();
        } else {
            return currentSession().createCriteria(SubProcess.class).createAlias("members", "m")
                    .add(Restrictions.eq("m.userName", userName)).list();
        }
    }

    @Override
    public boolean deleteProcess(Integer id) {
        Process process = getProcess(id);
        if (process != null) {
            currentSession().delete(process);
            return true;
        }
        return false;
    }

    @Override
    public SubProcess saveSubProcess(SubProcess subProcess) {
        currentSession().saveOrUpdate(subProcess);
        return subProcess;
    }

    @Override
    public SubProcess getSubProcess(Integer id) {
        return (SubProcess) currentSession().get(SubProcess.class, id);
    }

    @Override
    public SubProcess getSubProcess(String name) {
        List<SubProcess> subProcess = currentSession().createCriteria(SubProcess.class).add(Restrictions.eq("name", name)).list();
        return subProcess.size() > 0 ? subProcess.get(0) : null;
    }

    @Override
    public List<Process> getAllSubProcess(Integer entityId) {
        return currentSession().createCriteria(SubProcess.class).createAlias("entity", "e")
                .add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public List<SubProcess> getAllSubProcesses(Integer processId) {
        List<SubProcess> subProcesses = currentSession().createCriteria(SubProcess.class)
                .createAlias("parent", "p").add(Restrictions.eq("p.id", processId)).list();
        return subProcesses;
    }

    @Override
    public boolean deleteSubProcess(Integer id) {
        SubProcess subProcess = getSubProcess(id);
        if (subProcess != null) {
            currentSession().delete(subProcess);
            return true;
        }
        return false;
    }

    @Override
    public Objective saveObjective(Objective objective) {
        currentSession().saveOrUpdate(objective);
        return objective;
    }

    @Override
    public Objective getObjective(Integer id) {
        return (Objective) currentSession().get(Objective.class, id);
    }

    @Override
    public List<Objective> getAllObjectivesWithRisk(Integer entityId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Objective.class, "obj");
        detachedCriteria.createAlias("obj.risks", "r").setProjection(Property.forName("id"));
        return currentSession().createCriteria(Objective.class).createAlias("process", "p").createAlias("p.entity", "e")
                .add(Restrictions.and(Restrictions.eq("e.id", entityId), Property.forName("id").in(detachedCriteria))).list();
    }

    @Override
    public List<Objective> getAllObjectivesWithRiskFromProcess(Integer processId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Objective.class, "obj");
        detachedCriteria.createAlias("obj.risks", "r").setProjection(Property.forName("id"));
        return currentSession().createCriteria(Objective.class).createAlias("process", "p")
                .add(Restrictions.and(Restrictions.eq("p.id", processId), Property.forName("id").in(detachedCriteria))).list();
    }

    @Override
    public boolean deleteObjective(Integer id) {
        Objective objective = getObjective(id);
        if (objective != null) {
            currentSession().delete(objective);
            return true;
        }
        return false;
    }
}
