package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.ProcessDAO;
import com.verynet.gcint.api.model.GeneralProcess;
import com.verynet.gcint.api.model.Objective;
import com.verynet.gcint.api.model.Process;
import com.verynet.gcint.api.model.SubProcess;
import com.verynet.gcint.api.services.ProcessService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 05/09/2016.
 */
@Transactional
public class ProcessServiceImpl implements ProcessService {
    private ProcessDAO dao;

    @Override
    public void setProcessDAO(ProcessDAO dao) {
        this.dao = dao;
    }

    @Override
    public Process saveProcess(Process process) {
        return dao.saveProcess(process);
    }

    @Override
    @Transactional(readOnly = true)
    public Process getProcess(Integer id) {
        return dao.getProcess(id);
    }

    @Override
    public GeneralProcess getGeneralProcess(Integer id) {
        return dao.getGeneralProcess(id);
    }

    @Override
    public List<GeneralProcess> getAllGeneralProcess(Integer entityId) {
        return dao.getAllGeneralProcess(entityId);
    }

    @Override
    @Transactional(readOnly = true)
    public Process getHeavyProcess(Integer id) {
        Process process = getProcess(id);
        if (process != null) {
            Hibernate.initialize(process.getMembers());
        }
        return process;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Process> getAllProcess() {
        return dao.getAllProcess();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Process> getAllProcess(Integer entityId) {
        return dao.getAllProcess(entityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Process> getAllHeavyProcess(Integer entityId) {
        List<Process> processes = getAllProcess(entityId);
        for (int i = 0; i < processes.size(); i++) {
            Hibernate.initialize(processes.get(i).getMembers());
        }
        return processes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Process> getAllProcesses(String userName, boolean responsible) {
        return dao.getAllProcesses(userName, responsible);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubProcess> getAllSubProcesses(String userName, boolean responsible) {
        return dao.getAllSubProcesses(userName, responsible);
    }

    @Override
    public boolean deleteProcess(Integer id) {
        return dao.deleteProcess(id);
    }

    @Override
    public SubProcess saveSubProcess(SubProcess subProcess) {
        return dao.saveSubProcess(subProcess);
    }

    @Override
    @Transactional(readOnly = true)
    public SubProcess getSubProcess(Integer id) {
        return dao.getSubProcess(id);
    }

    @Override
    public SubProcess getHeavySubProcess(Integer id) {
        SubProcess subProcess = getSubProcess(id);
        if (subProcess != null) {
            Hibernate.initialize(subProcess.getMembers());
        }
        return subProcess;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubProcess> getAllSubProcesses(Integer processId) {
        return dao.getAllSubProcesses(processId);
    }

    @Override
    public List<SubProcess> getAllHeavySubProcesses(Integer processId) {
        List<SubProcess> subProcesses = getAllSubProcesses(processId);
        for (int i = 0; i < subProcesses.size(); i++) {
            Hibernate.initialize(subProcesses.get(i).getMembers());
        }
        return subProcesses;
    }

    @Override
    public boolean deleteSubProcess(Integer id) {
        return dao.deleteSubProcess(id);
    }

    @Override
    public Objective saveObjective(Objective objective) {
        return dao.saveObjective(objective);
    }

    @Override
    @Transactional(readOnly = true)
    public Objective getObjective(Integer id) {
        return dao.getObjective(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Objective> getAllObjectivesWithRisk(Integer entityId) {
        return dao.getAllObjectivesWithRisk(entityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Objective> getAllObjectivesWithRiskFromProcess(Integer processId) {
        return dao.getAllObjectivesWithRiskFromProcess(processId);
    }

    @Override
    public boolean deleteObjective(Integer id) {
        return dao.deleteObjective(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Objective> getObjectivesMoreAffected(Integer processId, Integer entityId, int limit) {
        List<Objective> objectives;
        if (processId != -1) {
            objectives = getAllObjectivesWithRiskFromProcess(processId);
        } else {
            objectives = getAllObjectivesWithRisk(entityId);
        }
        for (int i = 0; i < objectives.size() - 1; i++) {
            for (int j = i + 1; j < objectives.size(); j++) {
                if (objectives.get(j).isMoreAffected(objectives.get(i))) {
                    Objective temp = objectives.get(j);
                    objectives.set(j, objectives.get(i));
                    objectives.set(i, temp);
                }
            }
        }
        if (limit < objectives.size()) {
            for (int i = limit; i < objectives.size(); i++) {
                objectives.remove(i--);
            }
        }
        return objectives;
    }
}
