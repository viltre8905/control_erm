package com.verynet.gcint.controllers.admin.task;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Task;
import com.verynet.gcint.controllers.GeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 16/02/2017.
 */
@Controller
@RequestMapping(value = "/admin/task")
public class TaskController extends GeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public String getAllTasks() {
        return "admin/task/tasks";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/initTask")
    public
    @ResponseBody
    Map initTask(@RequestParam(value = "id") Integer id, @RequestParam(value = "frequency") Integer frequency) {
        Map<String, Object> result = new HashMap<>();
        try {
            Task task = Context.getTaskService().getTask(id);
            task.setStopped(false);
            task.setFrequency(frequency);
            Context.getTaskService().saveTask(task);
            if (!Context.getTaskThread().isAlive()) {
                Context.getTaskThread().clone().start();
            }
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error initializing task: %s", e.getMessage()));
            result.put("message", "Ha ocurrido un error inesperado");
            result.put("success", false);

        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/stopTask")
    public
    @ResponseBody
    Map stopTask(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Task task = Context.getTaskService().getTask(id);
            task.setStopped(true);
            Context.getTaskService().saveTask(task);
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error stopping task: %s", e.getMessage()));
            result.put("message", "Ha ocurrido un error inesperado");
            result.put("success", false);
        }
        return result;
    }

    @ModelAttribute(value = "tasks")
    public List<Task> getModelTasks() {
        return Context.getTaskService().getAllTasks();
    }
}
