package com.verynet.gcint.controllers.templates;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.enums.Roles;
import com.verynet.gcint.api.util.report.JasperUtil;
import com.verynet.gcint.controllers.CommonGeneralController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 24/09/2016.
 */
@Controller
public abstract class GuideTemplateController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ControllerUtil controllerUtil;
    private ResourceLoader resourceLoader;
    private DataSource dataSource;
    private JasperUtil jasperUtil;
    private final String REPORT_NAME = "Cuestionarios.pdf";

    @Autowired
    public GuideTemplateController(ControllerUtil controllerUtil, ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        this.controllerUtil = controllerUtil;
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
        this.jasperUtil = jasperUtil;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/affirmativeAnswer/create")
    public
    @ResponseBody
    Map createAffirmativeAnswer(@RequestParam(value = "id") Integer questionId,
                                @RequestParam(value = "description") String description,
                                @RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "type", required = false) Integer typeId,
                                @RequestParam(value = "procedence", required = false) Integer procedenceId,
                                MultipartHttpServletRequest request,
                                HttpSession session) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Question question = Context.getQuestionService().getQuestion(questionId);
            if (question.getProcedure() && typeId == null) {
                result.put("success", false);
                result.put("message", "Debe subir el manual de procedimiento");
            } else {
                AffirmativeAnswer affirmativeAnswer = new AffirmativeAnswer();
                Integer processId = getProcessSelected(session);
                GeneralProcess process = Context.getProcessService().getGeneralProcess(processId);
                affirmativeAnswer.setProcess(process);
                affirmativeAnswer.setComponent(Context.getNomenclatureService().getComponent(getComponent()));
                Evidence evidence = new Evidence();
                evidence.setDescription(description);
                DocumentMetadata documentMetadata = controllerUtil.handleMultipartRequest(request);
                if (documentMetadata != null) {
                    Document document = new Document();
                    document.setTitle(title);
                    document.setDocumentType(Context.getNomenclatureService().getDocumentType(typeId));
                    document.setDocumentProcedence(Context.getNomenclatureService().getDocumentProcedence(procedenceId));
                    document.setDocumentMetadata(documentMetadata);
                    document = Context.getDocumentService().saveDocument(document);
                    evidence.setDocument(document);
                }
                evidence = Context.getActivityService().saveEvidence(evidence);
                affirmativeAnswer.setEvidence(evidence);
                affirmativeAnswer = (AffirmativeAnswer) Context.getAnswerService().saveAnswer(affirmativeAnswer);
                Context.getQuestionService().setAnswer(questionId, affirmativeAnswer);
                result.put("success", true);
            }
        } catch (Exception e) {
            logger.error(String.format("Error creating affirmative answer(Component: %s): %s", getComponent(), e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rejectAnswer/create")
    public
    @ResponseBody
    Map createRejectAnswer(@RequestParam(value = "id") Integer questionId,
                           @RequestParam(value = "description") String description,
                           HttpSession session) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            RejectAnswer rejectAnswer = new RejectAnswer();
            Integer processId = getProcessSelected(session);
            GeneralProcess process = Context.getProcessService().getGeneralProcess(processId);
            rejectAnswer.setProcess(process);
            rejectAnswer.setDescription(description);
            rejectAnswer.setComponent(Context.getNomenclatureService().getComponent(getComponent()));
            rejectAnswer = (RejectAnswer) Context.getAnswerService().saveAnswer(rejectAnswer);
            Context.getQuestionService().setAnswer(questionId, rejectAnswer);
        } catch (Exception e) {
            logger.error(String.format("Error creating reject answer(Component: %s): %s", getComponent(), e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/negativeAnswer/create")
    public
    @ResponseBody
    Map createNegativeAnswer(@RequestParam(value = "id") Integer questionId,
                             @RequestParam(value = "deficiency") String deficiencyBody,
                             HttpSession session) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            NegativeAnswer negativeAnswer = new NegativeAnswer();
            Integer processId = getProcessSelected(session);
            GeneralProcess process = Context.getProcessService().getGeneralProcess(processId);
            negativeAnswer.setProcess(process);
            Deficiency deficiency = new Deficiency();
            deficiency.setBody(deficiencyBody);
            deficiency = Context.getDeficiencyService().saveDeficiency(deficiency);
            negativeAnswer.setDeficiency(deficiency);
            negativeAnswer.setComponent(Context.getNomenclatureService().getComponent(getComponent()));
            negativeAnswer = (NegativeAnswer) Context.getAnswerService().saveAnswer(negativeAnswer);
            Context.getQuestionService().setAnswer(questionId, negativeAnswer);
        } catch (Exception e) {
            logger.error(String.format("Error creating negative answer(Component: %s): %s", getComponent(), e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/evidence")
    public
    @ResponseBody
    Map getEvidence(@RequestParam(value = "id") Integer id, @RequestParam(value = "processId") Integer processId, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            Answer answer = Context.getQuestionService().getQuestion(id).answerFromProcess(processId);
            result.put("success", true);
            if (answer instanceof AffirmativeAnswer) {
                result.put("description", ((AffirmativeAnswer) answer).getEvidence().getDescription());
                Document document = ((AffirmativeAnswer) answer).getEvidence().getDocument();
                if (document != null) {
                    result.put("title", document.getTitle());
                    result.put("type", document.getDocumentType().getName());
                    result.put("procedence", document.getDocumentProcedence().getName());
                    result.put("fileName", String.format("%s.%s", document.getDocumentMetadata().getFileName(), document.getDocumentMetadata().getExtension()));
                    result.put("id", document.getDocumentMetadata().getId());
                }
            } else if (answer instanceof NegativeAnswer) {
                result.put("description", ((NegativeAnswer) answer).getDeficiency().getBody());
            } else {
                result.put("description", ((RejectAnswer) answer).getDescription());
            }
        } catch (Exception e) {
            logger.warn(String.format("Error getting answer evidence(Component: %s): %s", getComponent(), e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/document", produces = "multipart/form-data")
    public void getDocumentFile(@RequestParam(value = "id") String id, HttpServletResponse response) {
        DocumentMetadata documentMetadata = Context.getDocumentService().getDocumentMetadata(id);
        try {
            controllerUtil.handleMultipartResponse(documentMetadata, response);
        } catch (IOException io) {
            logger.warn(String.format("Error downloading answer evidence document(%s)(Component: %s): %s", id, getComponent(), io.getMessage()));
            io.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create_report")
    public
    @ResponseBody
    Map createReport(@RequestParam(value = "value", required = true) Integer value, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Resource r;
        switch (value) {
            case 1:
                r = resourceLoader.getResource("classpath:/reports/cuestionarios_sin_respuestas.jasper");
                break;
            case 2:
                r = resourceLoader.getResource("classpath:/reports/question_yes.jasper");
                break;
            case 3:
                r = resourceLoader.getResource("classpath:/reports/question_negative.jasper");
                break;
            case 4:
                r = resourceLoader.getResource("classpath:/reports/question_reject.jasper");
                break;
            default:
                r = resourceLoader.getResource("classpath:/reports/question_no_answer.jasper");
        }
        try {
            String jasperPath = r.getFile().getPath();
            HashMap<String, Object> params = new HashMap<>();
            params.put("processId", getProcessSelected(session));
            params.put("componentId", Context.getNomenclatureService().getComponent(getComponent()).getId());
            JasperPrint jasperPrint = jasperUtil.generateReport(jasperPath, params, dataSource.getConnection());
            jasperUtil.exportReport(jasperPrint, null, null, REPORT_NAME);
            result.put("success", true);
        } catch (IOException | SQLException | JRException e) {
            logger.warn(String.format("Error creating report(Component: %s): %s", getComponent(), e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/export", produces = "multipart/form-data")
    public void exportReport(HttpServletResponse response) {
        try {
            jasperUtil.handleMultipartResponse(REPORT_NAME, response);
        } catch (IOException e) {
            logger.warn(String.format("Error exporting report(Component: %s): %s", getComponent(), e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/guides")
    public String getGuides(ModelMap map, HttpSession session) {
        map.put("processSelected", getProcessSelected(session));
        map.put("objectProcessSelected", getObjectProcessSelected(session));
        User user = getUserLogged();
        if (user.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString()) ||
                user.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString()) ||
                user.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
            map.put("guides", Context.getGuideService().getAllDeepHeavyGuides(getComponent(), getProcessSelected(session)));
        } else {
            List<Map<String, List<Guide>>> mapList = new ArrayList<>();
            HashMap<String, List<Guide>> listHashMap = new HashMap<>();
            List<Guide> guides = Context.getGuideService().getAllHeavyGuides(getComponent(), getProcessSelected(session));
            if (guides.size() > 0) {
                listHashMap.put("", guides);
                mapList.add(listHashMap);
            }
            map.put("guides", mapList);
        }
        return getView();
    }

    public abstract String getComponent();

    public abstract String getView();

    @ModelAttribute("documentTypes")
    public List<DocumentType> getAllDocumentTypes() {
        return Context.getNomenclatureService().getAllDocumentTypes();
    }

    @ModelAttribute("documentProcedences")
    public List<DocumentProcedence> getAllDocumentProcedences() {
        return Context.getNomenclatureService().getAllDocumentProcedences();
    }

}
