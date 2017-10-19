package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.GeneralProcess;
import com.verynet.gcint.api.model.Objective;
import com.verynet.gcint.api.model.Process;
import com.verynet.gcint.api.model.SubProcess;

import java.util.List;

/**
 * Created by day on 05/09/2016.
 */
public interface ProcessDAO {
    public Process saveProcess(Process process);

    public Process getProcess(Integer id);

    public GeneralProcess getGeneralProcess(Integer id);

    public List<GeneralProcess> getAllGeneralProcess(Integer entityId);

    public Process getProcess(String name);

    public List<Process> getAllProcess();

    public List<Process> getAllProcess(Integer entityId);

    public List<GeneralProcess> getAllEntityProcess(Integer entityId);

    public List<Process> getAllProcesses(String userName, boolean responsible);

    public List<SubProcess> getAllSubProcesses(String userName, boolean responsible);

    public boolean deleteProcess(Integer id);

    public SubProcess saveSubProcess(SubProcess subProcess);

    public SubProcess getSubProcess(Integer id);

    public SubProcess getSubProcess(String name);

    public List<Process> getAllSubProcess(Integer entityId);

    public List<SubProcess> getAllSubProcesses(Integer processId);

    public boolean deleteSubProcess(Integer id);

    public Objective saveObjective(Objective objective);

    public Objective getObjective(Integer id);

    public List<Objective> getAllObjectivesWithRisk(Integer entityId);

    public List<Objective> getAllObjectivesWithRiskFromProcess(Integer processId);

    public boolean deleteObjective(Integer id);
}
