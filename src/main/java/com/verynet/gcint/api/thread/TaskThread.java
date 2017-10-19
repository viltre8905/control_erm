package com.verynet.gcint.api.thread;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.exceptions.InvalidDirectoryException;
import com.verynet.gcint.api.model.Task;
import com.verynet.gcint.api.util.enums.TaskNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by day on 17/02/2017.
 */
public class TaskThread extends Thread {
    @Value("${task.directory}")
    private String taskDirectory;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${database.name}")
    private String dbname;
    @Value("${task.backup.dump}")
    private String dump;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            List<Task> taskList = Context.getTaskService().getAllTasks();
            int countStopped = 0;
            for (Task task : taskList) {
                if (task.isStopped()) {
                    countStopped++;
                }
                if (task.getTaskName().getName().equals(TaskNames.Backup.toString()) && !task.isStopped()) {
                    boolean mustSave = false;
                    Date lastExecution = new Date();
                    if (task.getLastExecution() != null) {
                        Date today = new Date();
                        Date end = new Date(task.getLastExecution().getTime() + task.getFrequency() * (long) 86400000);
                        if (!today.before(end)) {
                            mustSave = true;
                        }
                    } else {
                        mustSave = true;
                    }
                    if (mustSave) {
                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(lastExecution);
                            String path = String.format("%s%sgcint(%s-%s-%s).sql", getDirectory().getAbsolutePath(),
                                    File.separator, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                            Runtime r = Runtime.getRuntime();
                            if (dump.equals("default")) {
                                dump = "pg_dump";
                            } else {
                                dump = String.format("%s\\pg_dump", dump);
                            }
                            r.exec(String.format("%s -f %s -U %s %s", dump, path, username, dbname));
                            task.setLastExecution(lastExecution);
                            Context.getTaskService().saveTask(task);

                        } catch (Exception e) {
                            logger.warn("Error creating backup: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (countStopped == taskList.size()) {
                flag = false;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                logger.warn("Interrupted sleep in task thread");
            }
        }
    }

    private File getDirectory() {
        File file;
        if (taskDirectory.equals("default")) {
            file = new File("task");
        } else {
            file = new File(taskDirectory);
        }
        boolean exist = false;
        if (!file.exists()) {
            exist = file.mkdir();
        } else {
            exist = true;
        }
        if (!exist) {
            throw new InvalidDirectoryException("The directory to save documents does not exist");
        }
        return file;
    }

    public void setTaskDirectory(String taskDirectory) {
        this.taskDirectory = taskDirectory;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public void setDump(String dump) {
        this.dump = dump;
    }

    public TaskThread clone() {
        TaskThread taskThread = new TaskThread();
        taskThread.setDbname(this.dbname);
        taskThread.setDump(this.dump);
        taskThread.setPassword(this.password);
        taskThread.setTaskDirectory(this.taskDirectory);
        taskThread.setUsername(this.username);
        return taskThread;
    }
}
