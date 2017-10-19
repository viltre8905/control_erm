<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 19);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Identificación de Riesgos</title>
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
                        Identificación de Riesgos
                    </h3>
                </div>
                <p>A continuaci&oacute;n se detallan los riesgos agrupados por los objetivos del proceso.
                </p>
                <p>Ficha de riesgo: <a href="#" id="exportReport"><img alt="" class="icon-pdf"
                                                                data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                width="25" height="25">
                </a></p>
                <p>Plan de prevención de riesgos: <a href="#" id="exportRiskPlanReport"><img alt="" class="icon-pdf"
                                                                       data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                       data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                       width="25" height="25">
                </a></p>
                <div class="progress-bar-container hidden" id="progress-bar-export-report">
                    <div class="col-sm-1 text-center center-scale">
                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                        </div>
                    </div>
                </div>
                <br/>
                <div class="windows">
                    <h5>
                        ${objectProcessSelected.name}
                    </h5>
                </div>
                <!-- RISK PANELS -->
                <div class="panel panel-group panel-transparent" data-toggle="collapse" id="accordion">
                    <c:forEach var="objective" items="${objectives}">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a class="collapsed" data-parent="#accordion" data-toggle=
                                            "collapse" href="#collapse${objective.id}">${objective.objective}</a>
                                </h4>
                            </div>
                            <div class="panel-collapse collapse" id="collapse${objective.id}">
                                <div class="panel-body">
                                    <div class="btn-group pull-right">
                                        <c:if test='${objectProcessSelected.responsible.id==userLogged.id}'>
                                            <button onclick="showAddRisk(${objective.id})" class="btn btn-primary"
                                                    type="button">Nuevo Riesgo
                                            </button>
                                        </c:if>
                                    </div>
                                    <div id="tableWithSearch_wrapper" class="dataTables_wrapper form-inline no-footer">
                                        <div class="table-responsive">
                                            <table id="tableWithSearch${objective.id}"
                                                   name="tableWithSearch"
                                                   class="table table-hover demo-table-search dataTable no-footer"
                                                   role="grid" aria-describedby="tableWithSearch_info">
                                                <thead>
                                                <tr role="row">
                                                    <th class="sorting_asc" tabindex="0" aria-controls="tableWithSearch"
                                                        rowspan="1"
                                                        colspan="1" style="width: 180px;" aria-sort="ascending">
                                                        Descripción
                                                    </th>
                                                    <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                        rowspan="1"
                                                        colspan="1" style="width: 80px;">Probabilidad
                                                    </th>
                                                    <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                        rowspan="1"
                                                        colspan="1"
                                                        style="width: 80px;">Impacto
                                                    </th>
                                                    <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                        rowspan="1"
                                                        colspan="1"
                                                        style="width: 80px;">Procedencia
                                                    </th>
                                                    <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                        rowspan="1"
                                                        colspan="1"
                                                        style="width: 80px;">Nivel
                                                    </th>
                                                    <th tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                                        colspan="1"
                                                        style="width: 80px;">Acciones
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="risk" items="${objective.risks}">
                                                    <tr role="row" class="odd">

                                                        <td class="v-align-middle semi-bold sorting_1">
                                                            <p>${risk.description}</p>
                                                        </td>
                                                        <td class="v-align-middle">
                                                            <p>${risk.probability}</p>
                                                        </td>
                                                        <td class="v-align-middle">
                                                            <p>${risk.impact}</p>
                                                        </td>
                                                        <td class="v-align-middle">
                                                            <p>${risk.Procedence()}</p>
                                                        </td>
                                                        <td class="v-align-middle">
                                                            <p>${risk.level}</p>
                                                        </td>
                                                        <td class="v-align-middle">
                                                            <div class="row">
                                                                <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                    <c:if test='${objectProcessSelected.responsible.id==userLogged.id}'>
                                                                        <button class="btn btn-success" type="button"
                                                                                onclick="fillRiskModal(this)"><i
                                                                                class="fa fa-pencil"></i>
                                                                        </button>
                                                                    </c:if>
                                                                    <a class="btn btn-success"
                                                                       data-toggle="tooltip"
                                                                       data-original-title="Actividades de control"
                                                                       onclick="location.href='activities?id=${risk.id}'"><i
                                                                            class="fa fa-history"></i>
                                                                    </a>
                                                                    <c:if test='${objectProcessSelected.responsible.id==userLogged.id}'>
                                                                        <button class="btn btn-success" type="button"
                                                                                onclick="deleteRisk(this)"><i
                                                                                class="fa fa-trash-o"></i>
                                                                        </button>
                                                                    </c:if>
                                                                    <a href="#" class="exportReport btn btn-success"
                                                                       data-toggle="tooltip"
                                                                       data-original-title="Ficha técnica"
                                                                       onclick="exportReportRisk(this)">
                                                                        <i class="fa fa-file-pdf-o"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <input type="text" hidden="true" value="${risk.id}"/>
                                                            <input type="text" hidden="true" value="${objective.id}"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="row">
                                            <div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <!-- END RISK PANELS -->
                <c:forEach var="subProcess" items="${subProcesses}">
                    <div class="windows">
                        <h5>
                                ${subProcess.name}
                        </h5>
                    </div>
                    <!-- RISK PANELS -->
                    <div class="panel panel-group panel-transparent" data-toggle="collapse"
                         id="accordion${subProcess.id}">
                        <c:forEach var="objective" items="${subProcess.objectives}">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a class="collapsed" data-parent="#accordion${subProcess.id}" data-toggle=
                                                "collapse"
                                           href="#collapse${objective.id}">${objective.objective}</a>
                                    </h4>
                                </div>
                                <div class="panel-collapse collapse" id="collapse${objective.id}">
                                    <div class="panel-body">
                                        <div id="tableWithSearch_wrapper${objective.id}"
                                             class="dataTables_wrapper form-inline no-footer">
                                            <div class="table-responsive">
                                                <table id="tableWithSearch${objective.id}"
                                                       name="tableWithSearch"
                                                       class="table table-hover demo-table-search dataTable no-footer"
                                                       role="grid" aria-describedby="tableWithSearch_info">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="sorting_asc" tabindex="0"
                                                            aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1" style="width: 180px;" aria-sort="ascending">
                                                            Descripción
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1" style="width: 80px;">Probabilidad
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1"
                                                            style="width: 80px;">Impacto
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1"
                                                            style="width: 80px;">Procedencia
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1"
                                                            style="width: 80px;">Nivel
                                                        </th>
                                                        <th tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                                            colspan="1"
                                                            style="width: 60px;">Acciones
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="risk" items="${objective.risks}">
                                                        <tr role="row" class="odd">

                                                            <td class="v-align-middle semi-bold sorting_1">
                                                                <p>${risk.description}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${risk.probability}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${risk.impact}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${risk.Procedence()}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${risk.level}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <div class="row">
                                                                    <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                        <button class="btn btn-success"
                                                                                type="button"
                                                                                onclick="showRiskData(this)"><i
                                                                                class="pg-contact_book"></i>
                                                                        </button>
                                                                        <a href="#" class="exportReport btn btn-success"
                                                                           data-toggle="tooltip"
                                                                           data-original-title="Ficha técnica"
                                                                           onclick="exportReportRisk(this)">
                                                                            <i class="fa fa-file-pdf-o"></i>
                                                                        </a>
                                                                    </div>
                                                                </div>
                                                                <input type="text" hidden="true"
                                                                       value="${risk.id}"/>
                                                                <input type="text" hidden="true"
                                                                       value="${objective.id}"/>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="row">
                                                <div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- END RISK PANELS -->
                </c:forEach>


                <!-- MODAL ADD RISK -->
                <div class="modal fade stick-up" id="modalAddRisk" tabindex="-1" role="dialog"
                     aria-labelledby="modalAddRisk" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header clearfix ">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                        onclick="cleanModalRisk();"><i
                                        class="pg-close fs-14"></i>
                                </button>
                                <h4 class="p-b-5"><span class="semi-bold">Nuevo</span> Riesgo
                                    <div class="pull-right hidden" id="progress-bar-add-risk">
                                        <div class="col-sm-1 text-center center-scale">
                                            <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                            </div>
                                        </div>
                                    </div>
                                </h4>
                                <div id="riskModalMessage"></div>
                            </div>
                            <div class="modal-body">
                                <form role="form">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Descripción</label>
                                                <textarea id="description"
                                                          name="description"
                                                          placeholder="Descripción del riesgo"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Posible manifestación negativa</label>
                                                <textarea id="generator"
                                                          name="generator"
                                                          placeholder="Se relaciona la posible forma mediante la cual se expresa una indisciplina, ilegalidad o manifestación de corrupción administrativa que puede poner en peligro el cumplimiento de los objetivos y la misión de la entidad"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default">
                                                <label>Activos</label>
                                                <textarea id="assets"
                                                          name="assets"
                                                          placeholder="Activos tangibles o intangibles afectados por la situación"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Causas</label>
                                                <textarea id="cause"
                                                          name="cause"
                                                          placeholder="Causas que permitieron que se generara la situación"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Consecuencias</label>
                                                <textarea id="consequence"
                                                          name="consequence"
                                                          placeholder="Consecuencias de la materialización de la situación"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <div class="form-group form-group-default">
                                                <label id="probabilityLabel">Probabilidad</label>
                                                <select id="probability" name="probability"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#probabilityLabel').removeAttr('class');">
                                                    <option value="Bajo" selected="selected">Bajo</option>
                                                    <option value="Tolerable">Tolerable</option>
                                                    <option value="Mediano">Mediano</option>
                                                    <option value="Alto">Alto</option>
                                                    <option value="Extremo">Extremo</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group form-group-default">
                                                <label id="impactLabel">Impacto</label>
                                                <select id="impact" name="impact"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#impactLabel').removeAttr('class');">
                                                    <option value="Bajo" selected="selected">Bajo</option>
                                                    <option value="Tolerable">Tolerable</option>
                                                    <option value="Mediano">Mediano</option>
                                                    <option value="Alto">Alto</option>
                                                    <option value="Extremo">Extremo</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group form-group-default">
                                                <label id="procedenceLabel">Procedencia</label>
                                                <select id="procedence" name="procedence"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#procedenceLabel').removeAttr('class');">
                                                    <option value="1" selected="selected">Interno</option>
                                                    <option value="2">Externo</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <button id="add-risk-button" class="btn btn-primary  btn-cons pull-right"
                                            onclick="if(validateModalRisk()){
                                            addRisk();
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
                <!-- END MODAL ADD RISK -->

                <!-- MODAL EDIT RISK -->
                <div class="modal fade stick-up" id="modalEditRisk" tabindex="-1" role="dialog"
                     aria-labelledby="modalEditRisk" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header clearfix ">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                        onclick="cleanModalRisk(true);"><i
                                        class="pg-close fs-14"></i>
                                </button>
                                <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Riesgo
                                    <div class="pull-right hidden" id="progress-bar-edit-risk">
                                        <div class="col-sm-1 text-center center-scale">
                                            <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                            </div>
                                        </div>
                                    </div>
                                </h4>
                                <div id="editRiskModalMessage"></div>
                            </div>
                            <div class="modal-body">
                                <form role="form">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Descripción</label>
                                                <textarea id="editDescription"
                                                          name="editDescription"
                                                          placeholder="Descripción del riesgo"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Posible manifestación negativa</label>
                                                <textarea id="editGenerator"
                                                          name="editGenerator"
                                                          placeholder="Se relaciona la posible forma mediante la cual se expresa una indisciplina, ilegalidad o manifestación de corrupción administrativa que puede poner en peligro el cumplimiento de los objetivos y la misión de la entidad"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default">
                                                <label>Activos</label>
                                                <textarea id="editAssets"
                                                          name="editAssets"
                                                          placeholder="Activos tangibles o intangibles afectados por la situación"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Causas</label>
                                                <textarea id="editCause"
                                                          name="editCause"
                                                          placeholder="Causas que permitieron que se generara la situación"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group form-group-default required">
                                                <label>Consecuencias</label>
                                                <textarea id="editConsequence"
                                                          name="editConsequence"
                                                          placeholder="Consecuencias de la materialización de la situación"
                                                          rows="1" class="full-width"
                                                          style="border: 0px;"
                                                          onfocus="$(this).attr('class','full-width focused')"
                                                          onblur="$(this).attr('class','full-width')"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <div class="form-group form-group-default">
                                                <label id="editProbabilityLabel">Probabilidad</label>
                                                <select id="editProbability" name="editProbability"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#editProbabilityLabel').removeAttr('class');">
                                                    <option value="Bajo" selected="selected">Bajo</option>
                                                    <option value="Tolerable">Tolerable</option>
                                                    <option value="Mediano">Mediano</option>
                                                    <option value="Alto">Alto</option>
                                                    <option value="Extremo">Extremo</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group form-group-default">
                                                <label id="editImpactLabel">Impacto</label>
                                                <select id="editImpact" name="editImpact"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#editImpactLabel').removeAttr('class');">
                                                    <option value="Bajo" selected="selected">Bajo</option>
                                                    <option value="Tolerable">Tolerable</option>
                                                    <option value="Mediano">Mediano</option>
                                                    <option value="Alto">Alto</option>
                                                    <option value="Extremo">Extremo</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group form-group-default">
                                                <label id="editProcedenceLabel">Procedencia</label>
                                                <select id="editProcedence" name="editProcedence"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#editProcedenceLabel').removeAttr('class');">
                                                    <option value="1" selected="selected">Interno</option>
                                                    <option value="2">Externo</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="button" id="edit-risk-button"
                                            class="btn btn-primary  btn-cons pull-right">Aceptar
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
                <!-- END MODAL EDIT RISK -->

                <!-- MODAL SHOW RISK -->
                <div class="modal fade stick-up" id="modalShowRisk" tabindex="-1" role="dialog"
                     aria-labelledby="modalShowRisk" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header clearfix ">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                        onclick="cleanModalShowRisk();"><i
                                        class="pg-close fs-14"></i>
                                </button>
                                <h4 class="p-b-5"><span class="semi-bold">Datos del</span> Riesgo
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form role="form">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <p><span class="span-semi-bold">Descripción: </span> <span
                                                    id="showDescription"></span></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <p><span class="span-semi-bold">Agentes generadores: </span> <span
                                                    id="showGenerator"></span></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <p><span class="span-semi-bold">Activos: </span> <span
                                                    id="showAssets"></span></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <p><span class="span-semi-bold">Causas: </span> <span
                                                    id="showCause"></span></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <p><span class="span-semi-bold">Consecuencias: </span> <span
                                                    id="showConsequence"></span></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <p><span class="span-semi-bold">Probabilidad: </span> <span
                                                    id="showProbability"></span></p>
                                        </div>
                                        <div class="col-sm-4">
                                            <p><span class="span-semi-bold">Impacto: </span> <span
                                                    id="showImpact"></span></p>
                                        </div>
                                        <div class="col-sm-4">
                                            <p><span class="span-semi-bold">Procedencia: </span> <span
                                                    id="showProcedence"></span></p>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- END MODAL SHOW RISK -->


            </div>
            <!-- BEGIN COPYRIGHT -->
            <%@include file="/WEB-INF/views/template/copyright.jsp" %>
            <!-- END COPYRIGHT -->
        </div>
        <!-- BEGIN JS -->
        <%@include file="/WEB-INF/views/template/script.jsp" %>
        <script src="${pageContext.request.contextPath}/resources/js/risk/management/identification.js"
                type="text/javascript"></script>
        <!-- END JS -->
    </div>

    <!-- BEGIN ID -->
    <input id="idRisk" type="text" hidden="true" value=""/>
    <input id="idObjective" type="text" hidden="true" value=""/>
    <script>

        var tables = document.getElementsByName("tableWithSearch");
        for (var i = 0; i < tables.length; i++) {
            var id = tables[i].getAttribute("id");
            generalInitTable(id);
        }
    </script>
    <!-- END ID -->
</div>
</body>
</html>
