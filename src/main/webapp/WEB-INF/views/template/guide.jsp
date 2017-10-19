<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cuestionarios</title>
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
</head>

<body class="fixed-header">

<%@include file="/WEB-INF/views/template/sideBar.jsp" %>
<!-- START PAGE-CONTAINER -->
<div class="page-container">

    <!-- START HEADER -->
    <%@include file="/WEB-INF/views/template/navBar.jsp" %>
    <!-- END HEADER -->
    <div class="page-content-wrapper">
        <div class="content">
            <div class="container-fluid container-fixed-lg">
                <div class="windows">
                    <h3>
                        Cuestionarios
                    </h3>
                </div>
                <p>A continuaci&oacute;n se detallan las preguntas a responder agrupadas por aspectos. Estas
                    preguntas
                    permitir&aacute;n conocer el estado de la entidad en cuanto al control interno.
                <p>
                <p><a href="#" id="exportReport1"><img alt="" class="icon-pdf"
                                                       data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                       data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                       src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                       width="25" height="25">

                </a>
                    Cuestionario
                    <a href="#" id="exportReport2" class="m-l-30"><img alt=""
                                                                       class="icon-pdf"
                                                                       data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                       data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       width="25"
                                                                       height="25">
                    </a>
                    Respuestas Positivas
                    <a href="#" id="exportReport3" class="m-l-30"><img alt=""
                                                                       class="icon-pdf"
                                                                       data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                       data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       width="25"
                                                                       height="25">
                    </a>
                    Respuestas Negativas
                    <a href="#" id="exportReport4" class="m-l-30"><img alt=""
                                                                       class="icon-pdf"
                                                                       data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                       data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       width="25"
                                                                       height="25">
                    </a>
                    No proceden
                    <a href="#" id="exportReport5" class="m-l-30"><img alt=""
                                                                       class="icon-pdf"
                                                                       data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                       data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       width="25"
                                                                       height="25">
                    </a>
                    Sin Responder
                </p>
                <div class="progress-bar-container" id="progress-bar-export-report">
                    <div class="col-sm-1 text-center center-scale">
                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                        </div>
                    </div>
                </div>
                <br/>
                <c:forEach var="guideList" items="${guides}">
                    <c:forEach var="processName" items="${guideList.keySet()}">
                        <div class="windows">
                            <h1 class="p-b-0 m-b-0">
                                    ${processName}
                            </h1>
                        </div>

                        <c:forEach var="guide" items="${guideList.get(processName)}">
                            <div class="windows">
                                <h5 class="p-b-0 m-b-0">
                                        ${guide.name}
                                </h5>
                            </div>
                            <!-- GUIDE PANELS -->
                            <div class="panel panel-group panel-transparent m-b-5" data-toggle="collapse"
                                 id="accordion">
                                <c:forEach var="aspect" items="${guide.orderedAspects()}">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a class="collapsed" data-parent="#accordion" data-toggle=
                                                        "collapse" href="#collapse${aspect.id}">${aspect.name}</a>
                                            </h4>
                                        </div>
                                        <div class="panel-collapse collapse" id="collapse${aspect.id}">
                                            <div class="panel-body">
                                                <div id="aspectTable${aspect.id}"
                                                     class="dataTables_wrapper form-inline no-footer">
                                                    <div class="table-responsive">
                                                        <table name="detailedTable"
                                                               class="table table-hover table-detailed"
                                                               id="detailedTable${aspect.id}">
                                                            <thead>
                                                            <tr>
                                                                <th style="width: 400px; background-color: #FAFAFA;">
                                                                    Título
                                                                    de
                                                                    la
                                                                    pregunta
                                                                </th>
                                                                <th style="width: 450px; background-color: #FAFAFA;">
                                                                    Respuesta
                                                                </th>
                                                                <th style="width: 60px; background-color: #FAFAFA;">
                                                                    Evidencia
                                                                </th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="question" items="${aspect.orderedQuestions()}">
                                                                <tr>
                                                                    <td class="v-align-middle semi-bold sorting_1 a1"
                                                                        style="background-color: #FAFAFA;">${question.title}</td>
                                                                    <td class="v-align-middle"
                                                                        style="background-color: #FAFAFA;">
                                                                        <c:choose>
                                                                            <c:when test="${question.getAnswerType(guide.process.id)==1}">
                                                                                <label for="switcheryYes${question.id}"
                                                                                       style="margin-left: 5%;">Si</label>
                                                                                <c:if test='${guide.process.responsible.id==userLogged.id}'>
                                                                                    <input id="switcheryYes${question.id}"
                                                                                           type="checkbox"
                                                                                           data-init-plugin="switchery"
                                                                                           checked="checked"
                                                                                           onchange="showAnswer(this,${question.id},1)"/>
                                                                                </c:if>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:if test='${guide.process.responsible.id==userLogged.id}'>
                                                                                    <label for="switcheryYes${question.id}"
                                                                                           style="margin-left: 5%;">Si</label>
                                                                                    <input id="switcheryYes${question.id}"
                                                                                           type="checkbox"
                                                                                           data-init-plugin="switchery"
                                                                                           onchange="showAnswer(this,${question.id},1)"/>
                                                                                </c:if>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <c:choose>
                                                                            <c:when test="${question.getAnswerType(guide.process.id)==2}">
                                                                                <label for="switcheryNO${question.id}"
                                                                                       style="margin-left: 5%;">No</label>
                                                                                <c:if test='${guide.process.responsible.id==userLogged.id}'>
                                                                                    <input id="switcheryNO${question.id}"
                                                                                           type="checkbox"
                                                                                           data-init-plugin="switchery"
                                                                                           checked="checked"
                                                                                           onchange="showAnswer(this,${question.id},2)"/>
                                                                                </c:if>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:if test='${guide.process.responsible.id==userLogged.id}'>
                                                                                    <label for="switcheryNO${question.id}"
                                                                                           style="margin-left: 5%;">No</label>
                                                                                    <input id="switcheryNO${question.id}"
                                                                                           type="checkbox"
                                                                                           data-init-plugin="switchery"
                                                                                           onchange="showAnswer(this,${question.id},2)"/>
                                                                                </c:if>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <c:choose>
                                                                            <c:when test="${question.getAnswerType(guide.process.id)==3}">
                                                                                <label for="switcheryNOP${question.id}"
                                                                                       style="margin-left:  5%;">No
                                                                                    procede</label>
                                                                                <c:if test='${guide.process.responsible.id==userLogged.id}'>
                                                                                    <input id="switcheryNOP${question.id}"
                                                                                           type="checkbox"
                                                                                           data-init-plugin="switchery"
                                                                                           checked="checked"
                                                                                           onchange="showAnswer(this,${question.id},3)"
                                                                                           style="padding-left:  5%;"/>
                                                                                </c:if>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:if test='${guide.process.responsible.id==userLogged.id}'>
                                                                                    <label for="switcheryNOP${question.id}"
                                                                                           style="margin-left:  5%;">No
                                                                                        procede</label>
                                                                                    <input id="switcheryNOP${question.id}"
                                                                                           type="checkbox"
                                                                                           data-init-plugin="switchery"
                                                                                           onchange="showAnswer(this,${question.id},3)"
                                                                                           style="padding-left:  5%;"/>
                                                                                </c:if>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <input type="text" hidden="true"
                                                                               value="${question.description}"/>
                                                                    </td>
                                                                    <td class="v-align-middle"
                                                                        style="background-color: #FAFAFA;">
                                                                        <button id="buttonEvidence${question.id}"
                                                                                class='btn btn-success <c:if test="${question.getAnswerType(guide.process.id)==0}">disabled</c:if>'
                                                                                type="button"
                                                                                onclick="showEvidence(this,${question.getAnswerType(guide.process.id)},${guide.process.id})">
                                                                            <i
                                                                                    class="pg-contact_book"></i>
                                                                        </button>
                                                                        <input type="text" hidden="true"
                                                                               value="${question.id}"/>
                                                                        <input id="process${question.id}" type="text" hidden="true"
                                                                               value="${guide.process.id}"/>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:forEach>
                </c:forEach>
                <!-- END GUIDE PANELS -->

                <!-- MODAL AFFIRMATIVE ANSWER -->
                <div class="modal fade stick-up" id="modalAffirmativeAnswer" tabindex="-1" role="dialog"
                     aria-labelledby="modalAffirmativeAnswer" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header clearfix ">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                        onclick="cleanModal(1);"><i
                                        class="pg-close fs-14"></i>
                                </button>
                                <h4 class="p-b-5"><span class="semi-bold">Evidencia</span>
                                    <div class="pull-right hidden" id="progress-bar-affirmative">
                                        <div class="col-sm-1 text-center center-scale">
                                            <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                            </div>
                                        </div>
                                    </div>
                                </h4>
                                <div id="affirmativeAnswerModalMessage"></div>
                            </div>
                            <div class="modal-body">
                                <form role="form" method="POST" enctype="multipart/form-data">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Descripción</label>
                                                <textarea id="affirmativeAnswerDescription"
                                                          name="affirmativeAnswerDescription"
                                                          placeholder="Descripción de la evidencia, dirección donde se encuentra, etc."
                                                          rows="5" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <p>Datos del documento (Opcional)</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default">
                                                <label>Título</label>
                                                <input id="documentTitle" name="documentTitle" type="text"
                                                       class="form-control"
                                                       placeholder="Nombre o título del documento" required="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <div class="form-group form-group-default">
                                                <label id="documentTypeLabel">Tipo de documento</label>
                                                <select id="documentType" name="documentType"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#documentTypeLabel').removeAttr('class');">
                                                    <option value="-1" selected="selected">Ninguno</option>
                                                    <c:forEach var="documentType" items="${documentTypes}">
                                                        <option value="${documentType.id}">${documentType.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group form-group-default">
                                                <label id="documentProcedenceLabel">Procedencia</label>
                                                <select id="documentProcedence" name="documentProcedence"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#documentProcedenceLabel').removeAttr('class');">
                                                    <option value="-1" selected="selected">Ninguno</option>
                                                    <c:forEach var="documentProcedence" items="${documentProcedences}">
                                                        <option value="${documentProcedence.id}">${documentProcedence.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div id="fileContainer" class="form-group form-group-default">
                                                <input type="file" id="file" name="file"/>
                                            </div>
                                        </div>
                                    </div>
                                    <button id="affirmative-button" class="btn btn-primary  btn-cons pull-right"
                                            onclick="if(validateAffirmativeAnswer()){
                                            setAffirmativeAnswer();
                                            } return false;">Aceptar
                                    </button>
                                </form>
                            </div>
                            <div class="modal-footer">
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- END MODAL AFFIRMATIVE ANSWER -->


                <!-- MODAL NEGATIVE ANSWER -->
                <div class="modal fade stick-up" id="modalNegativeAnswer" tabindex="-1" role="dialog"
                     aria-labelledby="modalNegativeAnswer" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header clearfix ">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                        onclick="cleanModal(2);"><i
                                        class="pg-close fs-14"></i>
                                </button>
                                <h4 class="p-b-5"><span class="semi-bold">Deficiencia</span>
                                    <div class="pull-right hidden" id="progress-bar-negative">
                                        <div class="col-sm-1 text-center center-scale">
                                            <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                            </div>
                                        </div>
                                    </div>
                                </h4>
                                <div id="negativeAnswerModalMessage"></div>
                            </div>
                            <div class="modal-body">
                                <form role="form" method="POST">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Deficiencia</label>
                                                <textarea id="negativeAnswerDescription"
                                                          name="negativeAnswerDescription"
                                                          placeholder="Breve descripción de la deficiencia detectada."
                                                          rows="5" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <button id="negative-button" class="btn btn-primary  btn-cons pull-right"
                                            onclick="if(validateNegativeAnswer()){
                                            setNegativeAnswer();
                                            } return false;">Aceptar
                                    </button>
                                </form>
                            </div>
                            <div class="modal-footer">
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- END MODAL NEGATIVE ANSWER -->

                <!-- MODAL REJECT ANSWER -->
                <div class="modal fade stick-up" id="modalRejectAnswer" tabindex="-1" role="dialog"
                     aria-labelledby="modalRejectAnswer" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header clearfix ">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                        onclick="cleanModal(3);"><i
                                        class="pg-close fs-14"></i>
                                </button>
                                <h4 class="p-b-5"><span class="semi-bold">Razón</span>
                                    <div class="pull-right hidden" id="progress-bar-reject">
                                        <div class="col-sm-1 text-center center-scale">
                                            <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                            </div>
                                        </div>
                                    </div>
                                </h4>
                                <div id="rejectAnswerModalMessage"></div>
                            </div>
                            <div class="modal-body">
                                <form role="form" method="POST">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Descripción</label>
                                                <textarea id="rejectDescription"
                                                          name="rejectDescription"
                                                          placeholder="Descripción del por qué no procede la pregunta."
                                                          rows="5" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <button id="reject-button" class="btn btn-primary  btn-cons pull-right"
                                            onclick="if(validateRejectAnswer()){
                                            setRejectAnswer();
                                            } return false;">Aceptar
                                    </button>
                                </form>
                            </div>
                            <div class="modal-footer">
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- END MODAL REJECT ANSWER -->

                <!-- MODAL EVIDENCE  -->
                <div class="modal fade stick-up" id="evidenceModal" tabindex="-1" role="dialog"
                     aria-labelledby="evidenceModal" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header clearfix ">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                        onclick="cleanActivitySolutionModal();"><i
                                        class="pg-close fs-14"></i>
                                </button>
                                <h4 class="p-b-5"><span class="semi-bold" id="evidenceTitle">Evidencia</span>
                                    <div class="pull-right hidden" id="progress-bar-add">
                                        <div class="col-sm-1 text-center center-scale">
                                            <div class="progress-circle-indeterminate m-t-0">
                                            </div>
                                        </div>
                                    </div>
                                </h4>
                                <div id="modalMessage"></div>
                            </div>
                            <div class="modal-body">
                                <form role="form" method="POST">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <p><span class="span-semi-bold">Descripción: </span> <span
                                                    id="description"></span></p>
                                        </div>
                                    </div>
                                    <div id="documentData" class="row hidden">
                                        <div class="col-sm-12">
                                            <span class="span-semi-bold">Datos del documento</span>
                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <p><span class="span-semi-bold">Título: </span> <span
                                                            id="title"></span>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <p><span class="span-semi-bold">Tipo de Documento: </span> <span
                                                            id="type"></span></p>
                                                </div>
                                                <div class="col-sm-6">
                                                    <p><span class="span-semi-bold">Procedencia: </span> <span
                                                            id="procedence"></span></p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-sm-10">
                                                    <p><span class="span-semi-bold">Archivo: </span> <span
                                                            id="fileName"></span></p>
                                                </div>
                                                <div class="col-sm-2">
                                                    <a id="download" class='btn btn-success'
                                                       type="button"
                                                       data-toggle="tooltip"
                                                       data-original-title="Descargar"
                                                       href="#"><i
                                                            class="pg-download"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- END MODAL EVIDENCE  -->

            </div>
            <!-- BEGIN COPYRIGHT -->
            <%@include file="/WEB-INF/views/template/copyright.jsp" %>
            <!-- END COPYRIGHT -->
        </div>
        <!-- BEGIN JS -->
        <%@include file="/WEB-INF/views/template/script.jsp" %>
        <script src="${pageContext.request.contextPath}/resources/js/template/activity.js"
                type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/template/guide.js"
                type="text/javascript"></script>
        <!-- END JS -->
    </div>

    <!-- BEGIN ID -->
    <input id="idQuestion" type="text" hidden="true" value=""/>
    <script>

        var tables = document.getElementsByName("detailedTable");
        for (var i = 0; i < tables.length; i++) {
            var id = tables[i].getAttribute("id");
            initDetailedViewTable(id);
        }
        exportAllGuideReports();
    </script>
    <!-- END ID -->
</div>
</body>
</html>
