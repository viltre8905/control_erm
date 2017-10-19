<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 43);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Medidas a Aplicar</title>
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
                <!-- START PANEL -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">Medidas a Aplicar
                        </div>
                        <div class="pull-right">
                            <security:authorize
                                    access="hasRole('ROLE_COMMITTEE_MEMBER')">
                                <div class="col-xs-11">
                                    <input type="text" placeholder="Buscar" class="form-control pull-right"
                                           id="search-table">
                                </div>
                            </security:authorize>
                            <security:authorize
                                    access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                <div class="col-xs-7">
                                    <input type="text" placeholder="Buscar" class="form-control pull-right"
                                           id="search-table">
                                </div>
                                <div class="btn-group pull-right col-xs-5">
                                    <button onclick="showAddReportActivity(${deficiency.id})" class="btn btn-primary"
                                            type="button">
                                        Adicionar
                                    </button>
                                </div>
                            </security:authorize>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="panel-body">
                        <div id="tableWithSearch_wrapper" class="dataTables_wrapper form-inline no-footer">
                            <div class="table-responsive">
                                <table id="tableWithSearch"
                                       class="table table-hover demo-table-search dataTable no-footer"
                                       role="grid" aria-describedby="tableWithSearch_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="sorting_asc" tabindex="0"
                                            aria-controls="tableWithSearch"
                                            rowspan="1"
                                            colspan="1" style="width: 200px;" aria-sort="ascending">
                                            Descripción
                                        </th>
                                        <th class="sorting" tabindex="0"
                                            aria-controls="tableWithSearch"
                                            rowspan="1"
                                            colspan="1" style="width: 80px;">Proceso
                                        </th>
                                        <th class="sorting" tabindex="0"
                                            aria-controls="tableWithSearch"
                                            rowspan="1"
                                            colspan="1" style="width: 120px;">Ejecutor
                                        </th>

                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                            rowspan="1"
                                            colspan="1"
                                            style="width: 80px;">Estado
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                            rowspan="1"
                                            colspan="1"
                                            style="width: 80px;">Fecha
                                        </th>
                                        <th tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 120px;">Acciones
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="activity"
                                               items="${deficiency.activities}">
                                        <tr role="row" class="odd">

                                            <td class="v-align-middle semi-bold sorting_1">
                                                <p>${activity.activityDescription}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${activity.process.name}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${activity.executor.firstName} ${activity.executor.lastName}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${activity.activityState.name}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${activity.toShortAccomplishDate()}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <div class="row">
                                                    <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                        <security:authorize
                                                                access="hasAnyRole('ROLE_SECRETARY_COMMITTEE','ROLE_COMMITTEE_MEMBER')">
                                                            <button class='btn btn-success <c:if test="${activity.activityState.name=='Asignada'}">disabled</c:if>'
                                                                    type="button"
                                                                    data-toggle="tooltip"
                                                                    data-original-title="Ver soluci&oacute;n"
                                                                    onclick="showActivitySolution(this)">
                                                                <i class="fa pg-contact_book"></i>
                                                            </button>
                                                            <security:authorize
                                                                    access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                                <button class='btn btn-success <c:if test="${activity.activityState.name!='Resuelta'}">disabled</c:if>'
                                                                        type="button"
                                                                        data-toggle="tooltip"
                                                                        data-original-title="Aceptar soluci&oacute;n"
                                                                        onclick="acceptSolution(this)">
                                                                    <i class="fa fa-thumbs-o-up"></i>
                                                                </button>
                                                                <button class='btn btn-success <c:if test="${activity.activityState.name!='Resuelta'}">disabled</c:if>'
                                                                        type="button"
                                                                        data-toggle="tooltip"
                                                                        data-original-title="Rechazar soluci&oacute;n"
                                                                        onclick="rejectSolution(this)">
                                                                    <i class="fa fa-thumbs-o-down"></i>
                                                                </button>
                                                                <button class='btn btn-success <c:if test="${activity.activityState.name=='Aceptada'}">disabled</c:if>'
                                                                        type="button"
                                                                        onclick="fillReportActivityModal(this)">
                                                                    <i class="fa fa-pencil"></i>
                                                                </button>
                                                                <button class='btn btn-success <c:if test="${activity.activityState.name=='Aceptada'}">disabled</c:if>'
                                                                        type="button"
                                                                        onclick="deleteReportActivity(this)">
                                                                    <i class="fa fa-trash-o"></i>
                                                                </button>
                                                            </security:authorize>
                                                        </security:authorize>
                                                    </div>
                                                </div>
                                                <input type="text" hidden="true"
                                                       value="${activity.id}"/>
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
                    <button class="btn btn-tag btn-tag-light pull-right m-t-5" type="button"
                            onclick="redirectURL('${pageContext.request.contextPath}/monitoring/committee/report/deficiency/all?id=${deficiency.actionControlInform.id}')">
                        Volver
                    </button>
                </div>
                <!-- END PANEL -->
            </div>

            <!-- MODAL ACTIVITY SOLUTION UP  -->
            <div class="modal fade stick-up" id="activitySolutionModal" tabindex="-1" role="dialog"
                 aria-labelledby="activitySolutionModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanActivitySolutionModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Evidencia</span>
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
                                                <p><span class="span-semi-bold">Título: </span> <span id="title"></span>
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
            <!-- END MODAL ACTIVITY SOLUTION UP  -->

            <!-- MODAL ADD ACTIVITY -->
            <div class="modal fade stick-up" id="modalActivity" tabindex="-1" role="dialog"
                 aria-labelledby="modalActivity" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanReportActivityModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nueva</span> Medida
                                <div class="pull-right hidden" id="progress-bar-add-activity">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="activityModalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Descripción</label>
                                            <textarea id="activityDescription"
                                                      name="activityDescription"
                                                      placeholder="Descripción de la medida."
                                                      rows="5" class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label id="activityProcessLabel">Proceso</label>
                                            <select id="activityProcess" name="activityProcess"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#activityProcessLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <option value="-2">Todos</option>
                                                <c:forEach var="process" items="${allProcesses}">
                                                    <option value="${process.id}">${process.name}
                                                        (${process.responsible.firstName} ${process.responsible.lastName})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default input-group required">
                                            <label>Fecha de cumplimiento</label>
                                            <input type="email" class="date1 form-control"
                                                   placeholder="Escoge una fecha"
                                                   id="date">
                                            <span class="input-group-addon">
                                             <i class="fa fa-calendar"></i>
                                             </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default">
                                            <label>Observación</label>
                                            <textarea id="activityObservation"
                                                      name="activityObservation"
                                                      placeholder="Alguna observación que se desee añadir"
                                                      rows="3" class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="add-activity-button" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if(validateReportActivity()){
                                addReportActivity();
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
            <!-- END ADD ACTIVITY -->

            <!-- MODAL EDIT ACTIVITY -->
            <div class="modal fade stick-up" id="editModalActivity" tabindex="-1" role="dialog"
                 aria-labelledby="editModalActivity" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanActivityModal(true);"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Medida
                                <div class="pull-right hidden" id="progress-bar-edit-activity">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="editActivityModalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Descripción</label>
                                            <textarea id="editActivityDescription"
                                                      name="editActivityDescription"
                                                      placeholder="Descripción de la medida."
                                                      rows="5" class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label id="editActivityProcessLabel">Proceso</label>
                                            <select id="editActivityProcess" name="documentType"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#editActivityProcessLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <c:forEach var="process" items="${allProcesses}">
                                                    <option value="${process.id}">${process.name}
                                                        (${process.responsible.firstName} ${process.responsible.lastName})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default input-group required">
                                            <label>Fecha de cumplimiento</label>
                                            <input type="email" class="date1 form-control"
                                                   placeholder="Escoge una fecha"
                                                   id="editDate">
                                            <span class="input-group-addon">
                                             <i class="fa fa-calendar"></i>
                                             </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default">
                                            <label>Observación</label>
                                            <textarea id="editActivityObservation"
                                                      name="editActivityObservation"
                                                      placeholder="Alguna observación que se desee añadir"
                                                      rows="3" class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-activity-button" class="btn btn-primary  btn-cons pull-right"
                                        type="button">Aceptar
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
            <!-- END EDIT ACTIVITY -->

        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/report/report.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/template/activity.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/general_committee.js"
            type="text/javascript"></script>
    <!-- END JS -->

    <!-- BEGIN ID -->
    <input id="idDeficiency" type="text" hidden="true" value=""/>
    <input id="idActivity" type="text" hidden="true" value=""/>
    <!-- END ID -->
</div>
</body>
</html>


