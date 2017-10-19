package com.verynet.gcint.controllers.admin.guide;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Aspect;
import com.verynet.gcint.api.model.Question;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.controllers.GeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by day on 10/09/2016.
 */
@Controller
@RequestMapping(value = "/admin/guide/question")
public class QuestionController extends GeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/questions")
    public String getQuestionFromGuide(@RequestParam(value = "id") Integer id, ModelMap map) {
        map.put("aspects", Context.getGuideService().getAllAspects(id));
        map.put("idGuide", id);
        map.put("guideName", Context.getGuideService().getGuide(id).getName());
        User loggedUser = getUserLogged();
        map.put("guides", Context.getGuideService().getAllGuidesFromEntity(loggedUser.getEntity().getId()));
        return "admin/guide/questions";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/aspect/create")
    public
    @ResponseBody
    Map<String, Object> createOrEditAspect(@RequestParam(value = "id", required = false) Integer id,
                                           @RequestParam(value = "idGuide", required = false) Integer idGuide,
                                           @RequestParam(value = "name") String name,
                                           @RequestParam(value = "number") Integer number,
                                           @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        Aspect aspect;
        if (action.equals("add")) {
            aspect = new Aspect();
        } else {
            aspect = Context.getGuideService().getAspect(id);
        }
        aspect.setName(name);
        aspect.setNo(number);
        aspect = Context.getGuideService().saveAspect(aspect);
        if (action.equals("add")) {
            Context.getGuideService().addAspect(idGuide, aspect);
            result.put("id", aspect.getId());
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/aspect/delete")
    public
    @ResponseBody
    boolean deleteAspect(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getGuideService().deleteAspect(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting aspect: %s", e.getMessage()));
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/aspect/upload")
    public
    @ResponseBody
    Map<String, Object> uploadAspects(@RequestParam(value = "idGuide") Integer idGuide,
                                      @RequestParam(value = "idGuideToLoad") Integer idGuideToLoad) {
        Map<String, Object> result = new HashMap<>();
        try {
            Context.getGuideService().saveAllAspects(idGuide, idGuideToLoad);
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error uploading aspect: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");

        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/questions/all")
    public
    @ResponseBody
    Map<String, Object> getAllQuestions(@RequestParam(value = "id", required = false) Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Question> questions = Context.getGuideService().getAllQuestions(id);
            List<Question> questionList = new ArrayList<>();
            if (questions != null) {
                for (Question question : questions) {
                    Question newQuestion = new Question();
                    newQuestion.setDescription(question.getDescription());
                    newQuestion.setCode(question.getCode());
                    newQuestion.setTitle(question.getTitle());
                    newQuestion.setId(question.getId());
                    newQuestion.setProcedure(question.getProcedure());
                    questionList.add(newQuestion);
                }
            }
            result.put("success", true);
            result.put("questions", questionList);
        } catch (Exception e) {
            logger.error(String.format("Error getting all question: %s", e.getMessage()));
            result.put("message", e.getMessage());
            result.put("success", false);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getQuestionData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Question question = Context.getQuestionService().getQuestion(id);
        if (question == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("code", question.getCode());
        result.put("title", question.getTitle());
        result.put("description", question.getDescription());
        result.put("procedure", question.getProcedure());
        result.put("description", question.getDescription());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map<String, Object> createOrEditQuestion(@RequestParam(value = "id", required = false) Integer id,
                                             @RequestParam(value = "idAspect", required = false) Integer idAspect,
                                             @RequestParam(value = "code") String code,
                                             @RequestParam(value = "title") String title,
                                             @RequestParam(value = "description") String description,
                                             @RequestParam(value = "procedure") Boolean procedure,
                                             @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        Question question;
        if (action.equals("add")) {
            question = new Question();
        } else {
            question = Context.getQuestionService().getQuestion(id);
        }
        question.setCode(code);
        question.setTitle(title);
        question.setDescription(description);
        question.setProcedure(procedure);
        question.setStart(new Date());
        question = Context.getQuestionService().saveQuestion(question);
        if (action.equals("add")) {
            Context.getGuideService().addQuestion(idAspect, question);
            result.put("id", question.getId());
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteQuestion(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getQuestionService().deleteQuestion(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting question: %s", e.getMessage()));
            return false;
        }
    }
}
