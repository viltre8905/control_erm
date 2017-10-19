<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
                <security:authorize
                        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                    <c:if test="${negativeAnswers.size()>0}">
                        <div class="windows">
                            <h3>
                                Medidas a Aplicar
                            </h3>
                        </div>
                        <p>A continuaci&oacute;n se detallan las medidas a aplicar agrupadas por preguntas cuyas
                            respuestas
                            fueron negativas, luego las activides que debes resolver y finalmente otras actividades que
                            hayas asignado.
                        </p>
                    </c:if>
                    <p><a href="#" id="exportReport"><img alt="" class="icon-pdf"
                                                          data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                          data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                          src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                          width="25" height="25">
                    </a>Imprimir</p>
                    <div class="progress-bar-container" id="progress-bar-export-report">
                        <div class="col-sm-1 text-center center-scale">
                            <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                            </div>
                        </div>
                    </div>
                    <br/>
                    <c:forEach var="negativeAnswersList" items="${negativeAnswerOfProcess}">
                        <c:forEach var="processName" items="${negativeAnswersList.keySet()}">
                            <div class="windows">
                                <h1 class="p-b-0 m-b-0">
                                        ${processName}
                                </h1>
                            </div>
                            <!-- ACTIVITIES PANELS -->
                            <div class="panel panel-group panel-transparent" data-toggle="collapse" id="accordion">
                                <c:forEach var="negativeAnswer" items="${negativeAnswersList.get(processName)}">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a class="collapsed" data-parent="#accordion" data-toggle=
                                                        "collapse"
                                                   href="#collapse${negativeAnswer.id}">${negativeAnswer.deficiency.body}</a>
                                            </h4>
                                        </div>
                                        <div class="panel-collapse collapse" id="collapse${negativeAnswer.id}">
                                            <div class="panel-body">
                                                <div class="btn-group pull-right">
                                                    <c:if test='${objectProcessSelected.responsible.id==userLogged.id}'>
                                                        <button onclick="showAddActivity(${negativeAnswer.id},1);$('#activityType').val(1);"
                                                                class="btn btn-primary"
                                                                type="button">Nueva Medida
                                                        </button>
                                                    </c:if>
                                                </div>
                                                <div id="tableWithSearch_wrapper"
                                                     class="dataTables_wrapper form-inline no-footer">
                                                    <div class="table-responsive">
                                                        <table id="tableWithSearch${negativeAnswer.id}"
                                                               name="tableWithSearch"
                                                               class="table table-hover demo-table-search dataTable no-footer"
                                                               role="grid" aria-describedby="tableWithSearch_info">
                                                            <thead>
                                                            <tr role="row">
                                                                <th class="sorting_asc" tabindex="0"
                                                                    aria-controls="tableWithSearch"
                                                                    rowspan="1"
                                                                    colspan="1" style="width: 200px;"
                                                                    aria-sort="ascending">
                                                                    Descripción
                                                                </th>
                                                                <c:if test='${objectProcessSelected.responsible.id!=userLogged.id}'>
                                                                    <th class="sorting" tabindex="0"
                                                                        aria-controls="tableWithSearch"
                                                                        rowspan="1"
                                                                        colspan="1" style="width: 120px;">Responsable
                                                                    </th>
                                                                </c:if>
                                                                <security:authorize
                                                                        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                    <security:authorize
                                                                            access="hasAnyRole('ROLE_PROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                        <c:if test='${objectProcessSelected.processType()==1}'>
                                                                            <th class="sorting" tabindex="0"
                                                                                aria-controls="tableWithSearch"
                                                                                rowspan="1"
                                                                                colspan="1" style="width: 80px;">
                                                                                Actividad o Sub-Proceso
                                                                            </th>
                                                                        </c:if>
                                                                    </security:authorize>
                                                                    <th class="sorting" tabindex="0"
                                                                        aria-controls="tableWithSearch"
                                                                        rowspan="1"
                                                                        colspan="1" style="width: 120px;">Ejecutor
                                                                    </th>
                                                                </security:authorize>
                                                                <th class="sorting" tabindex="0"
                                                                    aria-controls="tableWithSearch"
                                                                    rowspan="1"
                                                                    colspan="1"
                                                                    style="width: 80px;">Estado
                                                                </th>
                                                                <th class="sorting" tabindex="0"
                                                                    aria-controls="tableWithSearch"
                                                                    rowspan="1"
                                                                    colspan="1"
                                                                    style="width: 80px;">Fecha
                                                                </th>
                                                                <th tabindex="0" aria-controls="tableWithSearch"
                                                                    rowspan="1"
                                                                    colspan="1"
                                                                    style="width: 120px;">Acciones
                                                                </th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="activity"
                                                                       items="${negativeAnswer.deficiency.activities}">
                                                                <tr role="row" class="odd">

                                                                    <td class="v-align-middle semi-bold sorting_1">
                                                                        <p>${activity.activityDescription}</p>
                                                                    </td>
                                                                    <c:if test='${objectProcessSelected.responsible.id!=userLogged.id}'>
                                                                        <td class="v-align-middle">
                                                                            <p>${activity.responsible.firstName} ${activity.responsible.lastName}</p>
                                                                        </td>
                                                                    </c:if>
                                                                    <security:authorize
                                                                            access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                        <security:authorize
                                                                                access="hasAnyRole('ROLE_PROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                            <c:if test='${objectProcessSelected.processType()==1}'>
                                                                                <td class="v-align-middle">
                                                                                    <p>${activity.process.name}</p>
                                                                                </td>
                                                                            </c:if>
                                                                        </security:authorize>
                                                                        <td class="v-align-middle">
                                                                            <p>${activity.executor.firstName} ${activity.executor.lastName}</p>
                                                                        </td>

                                                                    </security:authorize>
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
                                                                                        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                                    <button class='btn btn-success <c:if test="${activity.activityState.name=='Asignada'}">disabled</c:if>'
                                                                                            type="button"
                                                                                            data-toggle="tooltip"
                                                                                            data-original-title="Ver soluci&oacute;n"
                                                                                            onclick="showActivitySolution(this)">
                                                                                        <i class="fa pg-contact_book"></i>
                                                                                    </button>
                                                                                    <c:if test='${objectProcessSelected.responsible.id==userLogged.id}'>
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
                                                                                                onclick="fillActivityModal(this,1)">
                                                                                            <i class="fa fa-pencil"></i>
                                                                                        </button>
                                                                                        <button class='btn btn-success <c:if test="${activity.activityState.name=='Aceptada'}">disabled</c:if>'
                                                                                                type="button"
                                                                                                onclick="deleteActivity(this,1)">
                                                                                            <i class="fa fa-trash-o"></i>
                                                                                        </button>
                                                                                    </c:if>
                                                                                </security:authorize>
                                                                            </div>
                                                                        </div>
                                                                        <input type="text" hidden="true"
                                                                               value="${activity.id}"/>
                                                                        <input type="text" hidden="true"
                                                                               value="${negativeAnswer.id}"/>
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
                            <!-- END ACTIVITIES PANELS -->
                        </c:forEach>
                    </c:forEach>
                    <c:forEach var="negativeAnswersList" items="${negativeAnswerOfSubProcess}">
                        <c:forEach var="processName" items="${negativeAnswersList.keySet()}">
                            <div class="windows">
                                <h1 class="p-b-0 m-b-0">
                                        ${processName}
                                </h1>
                            </div>
                            <!-- ACTIVITIES PANELS -->
                            <div class="panel panel-group panel-transparent" data-toggle="collapse" id="accordion">
                                <c:forEach var="negativeAnswer" items="${negativeAnswersList.get(processName)}">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a class="collapsed" data-parent="#accordion" data-toggle=
                                                        "collapse"
                                                   href="#collapse${negativeAnswer.id}">${negativeAnswer.deficiency.body}</a>
                                            </h4>
                                        </div>
                                        <div class="panel-collapse collapse" id="collapse${negativeAnswer.id}">
                                            <div class="panel-body">
                                                <div class="btn-group pull-right">
                                                </div>
                                                <div id="tableWithSearch_wrapper"
                                                     class="dataTables_wrapper form-inline no-footer">
                                                    <div class="table-responsive">
                                                        <table id="tableWithSearch${negativeAnswer.id}"
                                                               name="tableWithSearch"
                                                               class="table table-hover demo-table-search dataTable no-footer"
                                                               role="grid" aria-describedby="tableWithSearch_info">
                                                            <thead>
                                                            <tr role="row">
                                                                <th class="sorting_asc" tabindex="0"
                                                                    aria-controls="tableWithSearch"
                                                                    rowspan="1"
                                                                    colspan="1" style="width: 200px;"
                                                                    aria-sort="ascending">
                                                                    Descripción
                                                                </th>
                                                                <c:if test='${objectProcessSelected.responsible.id!=userLogged.id}'>
                                                                    <th class="sorting" tabindex="0"
                                                                        aria-controls="tableWithSearch"
                                                                        rowspan="1"
                                                                        colspan="1" style="width: 120px;">Responsable
                                                                    </th>
                                                                </c:if>
                                                                <security:authorize
                                                                        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                    <security:authorize
                                                                            access="hasAnyRole('ROLE_PROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                        <c:if test='${objectProcessSelected.processType()==1}'>
                                                                            <th class="sorting" tabindex="0"
                                                                                aria-controls="tableWithSearch"
                                                                                rowspan="1"
                                                                                colspan="1" style="width: 80px;">
                                                                                Actividad o Sub-Proceso
                                                                            </th>
                                                                        </c:if>
                                                                    </security:authorize>
                                                                    <th class="sorting" tabindex="0"
                                                                        aria-controls="tableWithSearch"
                                                                        rowspan="1"
                                                                        colspan="1" style="width: 120px;">Ejecutor
                                                                    </th>
                                                                </security:authorize>
                                                                <th class="sorting" tabindex="0"
                                                                    aria-controls="tableWithSearch"
                                                                    rowspan="1"
                                                                    colspan="1"
                                                                    style="width: 80px;">Estado
                                                                </th>
                                                                <th class="sorting" tabindex="0"
                                                                    aria-controls="tableWithSearch"
                                                                    rowspan="1"
                                                                    colspan="1"
                                                                    style="width: 80px;">Fecha
                                                                </th>
                                                                <th tabindex="0" aria-controls="tableWithSearch"
                                                                    rowspan="1"
                                                                    colspan="1"
                                                                    style="width: 120px;">Acciones
                                                                </th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="activity"
                                                                       items="${negativeAnswer.deficiency.activities}">
                                                                <tr role="row" class="odd">

                                                                    <td class="v-align-middle semi-bold sorting_1">
                                                                        <p>${activity.activityDescription}</p>
                                                                    </td>
                                                                    <c:if test='${objectProcessSelected.responsible.id!=userLogged.id}'>
                                                                        <td class="v-align-middle">
                                                                            <p>${activity.responsible.firstName} ${activity.responsible.lastName}</p>
                                                                        </td>
                                                                    </c:if>
                                                                    <security:authorize
                                                                            access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                        <security:authorize
                                                                                access="hasAnyRole('ROLE_PROCESS_SUPERVISORY', 'ROLE_GENERAL_SUPERVISORY')">
                                                                            <c:if test='${objectProcessSelected.processType()==1}'>
                                                                                <td class="v-align-middle">
                                                                                    <p>${activity.process.name}</p>
                                                                                </td>
                                                                            </c:if>
                                                                        </security:authorize>
                                                                        <td class="v-align-middle">
                                                                            <p>${activity.executor.firstName} ${activity.executor.lastName}</p>
                                                                        </td>

                                                                    </security:authorize>
                                                                    <td class="v-align-middle">
                                                                        <p>${activity.activityState.name}</p>
                                                                    </td>
                                                                    <td class="v-align-middle">
                                                                        <p>${activity.toShortAccomplishDate()}</p>
                                                                    </td>
                                                                    <td class="v-align-middle">
                                                                        <div class="row">
                                                                            <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                                <button class='btn btn-success <c:if test="${activity.activityState.name=='Asignada'}">disabled</c:if>'
                                                                                        type="button"
                                                                                        data-toggle="tooltip"
                                                                                        data-original-title="Ver soluci&oacute;n"
                                                                                        onclick="showActivitySolution(this)">
                                                                                    <i class="fa pg-contact_book"></i>
                                                                                </button>
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
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <!-- END ACTIVITIES PANELS -->
                        </c:forEach>
                    </c:forEach>
                </security:authorize>
                <security:authorize
                        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY', 'ROLE_EXECUTER', 'ROLE_ACTIVITY_RESPONSIBLE')">
                    <!-- START MY ACTIVITIES PANEL -->
                    <div id="myActivitiesContainer">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-title">Mis Medidas
                                </div>
                                <div class="pull-right">
                                    <div class="col-xs-12">
                                        <input type="text" placeholder="Buscar" class="form-control pull-right"
                                               id="search-table">
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="panel-body">
                                <div id="tableWithSearch_wrapper1" class="dataTables_wrapper form-inline no-footer">
                                    <div class="table-responsive">
                                        <table id="tableWithSearch"
                                               class="table table-hover demo-table-search dataTable no-footer"
                                               role="grid" aria-describedby="tableWithSearch_info">
                                            <thead>
                                            <tr role="row">
                                                <th class="sorting_asc" tabindex="0" aria-controls="tableWithSearch"
                                                    rowspan="1"
                                                    colspan="1" style="width: 250px;" aria-sort="ascending">Descripción
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                    rowspan="1"
                                                    colspan="1" style="width: 120px;">Observaciones
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

                                                <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                    style="width: 80px;">Acciones
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="activities" items="${activities}">
                                                <tr role="row" class="odd">
                                                    <td class="v-align-middle semi-bold sorting_1">
                                                        <p>${activities.activityDescription}</p>
                                                    </td>
                                                    <td class="v-align-middle">
                                                        <p>${activities.observation}</p>
                                                    </td>
                                                    <td class="v-align-middle">
                                                        <p>${activities.activityState.name}</p>
                                                    </td>
                                                    <td class="v-align-middle">
                                                        <p>${activities.toShortAccomplishDate()}</p>
                                                    </td>
                                                    <td class="v-align-middle">
                                                        <div class="row">
                                                            <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                <button class='btn btn-success <c:if test="${activities.activityState.name=='Aceptada'}">disabled</c:if>'
                                                                        type="button"
                                                                        data-toggle="tooltip"
                                                                        data-original-title="Ejecutar"
                                                                        onclick="doActivity(this)"><i
                                                                        class="pg-refresh"></i>
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <input type="text" hidden="true" value="${activities.id}"/>
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
                    <!-- END MY ACTIVITIES PANEL -->
                </security:authorize>
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

            <!-- MODAL AFFIRMATIVE ANSWER -->
            <div class="modal fade stick-up" id="modalAffirmativeAnswer" tabindex="-1" role="dialog"
                 aria-labelledby="modalAffirmativeAnswer" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanAffirmativeAnswerModal();"><i
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
                                <button id="affirmative-button" type="button"
                                        class="btn btn-primary  btn-cons pull-right"
                                >Aceptar
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

            <!-- MODAL ADD ACTIVITY -->
            <div class="modal fade stick-up" id="modalActivity" tabindex="-1" role="dialog"
                 aria-labelledby="modalActivity" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanActivityModal(false);"><i
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
                                            <security:authorize access="hasRole('ROLE_PROCESS_SUPERVISORY')">
                                                <label id="activitySubProcessLabel">Actividad o Sub-Proceso</label>
                                                <select id="activitySubProcess" name="documentType"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#activitySubProcessLabel').removeAttr('class');">
                                                    <option value="-1" selected="selected">Ninguno</option>
                                                    <option value="-2">Todos</option>
                                                    <c:forEach var="subProcess" items="${subProcesses}">
                                                        <option value="${subProcess.id}">${subProcess.name}
                                                            (${subProcess.responsible.firstName} ${subProcess.responsible.lastName})
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </security:authorize>
                                            <security:authorize access="hasRole('ROLE_SUBPROCESS_SUPERVISORY')">
                                                <label id="activityExecutorLabel">Ejecutor</label>
                                                <select id="activityExecutor" name="documentType"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#activityExecutorLabel').removeAttr('class');">
                                                    <option value="-1" selected="selected">Ninguno</option>
                                                    <option value="-2">Todos</option>
                                                    <c:forEach var="executor" items="${executors}">
                                                        <option value="${executor.id}">${executor.firstName} ${executor.lastName}</option>
                                                    </c:forEach>
                                                </select>
                                            </security:authorize>
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
                                        onclick="if(validateActivity(false)){
                                addActivity(1);
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
                                            <security:authorize access="hasRole('ROLE_PROCESS_SUPERVISORY')">
                                                <label id="editActivitySubProcessLabel">Actividad o Sub-Proceso</label>
                                                <select id="editActivitySubProcess" name="documentType"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#editActivitySubProcessLabel').removeAttr('class');">
                                                    <option value="-1" selected="selected">Ninguno</option>
                                                    <c:forEach var="subProcess" items="${subProcesses}">
                                                        <option value="${subProcess.id}">${subProcess.name}
                                                            (${subProcess.responsible.firstName} ${subProcess.responsible.lastName})
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </security:authorize>
                                            <security:authorize access="hasRole('ROLE_SUBPROCESS_SUPERVISORY')">
                                                <label id="editActivityExecutorLabel">Ejecutor</label>
                                                <select id="editActivityExecutor" name="documentType"
                                                        tabindex="-1" title=""
                                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                        onblur="$('#editActivityExecutorLabel').removeAttr('class');">
                                                    <option value="-1" selected="selected">Ninguno</option>
                                                    <c:forEach var="executor" items="${executors}">
                                                        <option value="${executor.id}">${executor.firstName} ${executor.lastName}</option>
                                                    </c:forEach>
                                                </select>
                                            </security:authorize>
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
    <script src="${pageContext.request.contextPath}/resources/js/template/guide.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/template/activity.js"
            type="text/javascript"></script>
    <!-- END JS -->
    <script>
        var table = $('#tableWithSearchOtherActivities');
        var settings = {
            "sDom": "<'table-responsive't><'row'<p i>>",
            "sPaginationType": "bootstrap",
            "destroy": true,
            "scrollCollapse": true,
            "oLanguage": {
                "sLengthMenu": "_MENU_ ",
                "sInfo": "Mostrando <b>_START_ al _END_</b> de _TOTAL_ total"
            },
            "iDisplayLength": 10
        };
        table.dataTable(settings);
        // search box for table
        $('#search-table-other-activities').keyup(function () {
            table.fnFilter($(this).val());
        });

        var tables = document.getElementsByName("tableWithSearch");
        for (var i = 0; i < tables.length; i++) {
            var id = tables[i].getAttribute("id");
            generalInitTable(id);
        }
    </script>

    <!-- BEGIN ID -->
    <input id="idActivity" type="text" hidden="true" value=""/>
    <input id="negativeAnswerId" type="text" hidden="true" value=""/>
    <input id="activityType" type="text" hidden="true" value=""/>
    <!-- END ID -->

