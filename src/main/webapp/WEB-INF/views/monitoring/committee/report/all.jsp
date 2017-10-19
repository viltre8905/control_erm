<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 43);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Informes de acciones de control</title>
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
                        Informes de acciones de control
                    </h3>
                </div>
                <p>A continuaci&oacute;n se detallan todos los informes de las acciones de control realizadas agrupados
                    por los diferentes tipos de acciones de control.
                </p>
                <br/>
                <!-- REPORTS PANELS -->
                <div class="panel panel-group panel-transparent" data-toggle="collapse" id="accordion">
                    <c:forEach var="controlActionName" items="${actionControlInformsMap.keySet()}">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a class="collapsed" data-parent="#accordion" data-toggle=
                                            "collapse"
                                       href="#collapse${controlActionName.split(",,,")[1]}">${controlActionName.split(",,,")[0]}</a>
                                </h4>
                            </div>
                            <div class="panel-collapse collapse" id="collapse${controlActionName.split(",,,")[1]}">
                                <div class="panel-body">
                                    <div class="btn-group pull-right">
                                        <security:authorize
                                                access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                            <button onclick="showAddReport(${controlActionName.split(",,,")[1]});"
                                                    class="btn btn-primary"
                                                    type="button">Nuevo Informe
                                            </button>
                                        </security:authorize>
                                    </div>
                                    <div id="tableWithSearch_wrapper"
                                         class="dataTables_wrapper form-inline no-footer">
                                        <div class="table-responsive">
                                            <table id="tableWithSearch${controlActionName.split(",,,")[1]}"
                                                   name="tableWithSearch"
                                                   class="table table-hover demo-table-search dataTable no-footer"
                                                   role="grid" aria-describedby="tableWithSearch_info">
                                                <thead>
                                                <tr role="row">
                                                    <th class="sorting_asc" tabindex="0"
                                                        aria-controls="tableWithSearch" rowspan="1"
                                                        colspan="1" style="width: 200px;" aria-sort="ascending">Título
                                                    </th>
                                                    <th tabindex="0"
                                                        aria-controls="tableWithSearch" rowspan="1"
                                                        colspan="1" style="width: 300px;" aria-sort="ascending">
                                                        Conclusiones
                                                    </th>
                                                    <th tabindex="0"
                                                        aria-controls="tableWithSearch" rowspan="1"
                                                        colspan="1" style="width: 200px;" aria-sort="ascending">
                                                        Ubicación
                                                    </th>

                                                    <th tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                                        colspan="1"
                                                        style="width: 150px;">Acciones
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="inform"
                                                           items="${actionControlInformsMap.get(controlActionName)}">
                                                    <tr role="row" class="odd">
                                                        <td class="v-align-middle semi-bold sorting_1">
                                                            <p>${inform.title}</p>
                                                        </td>
                                                        <td class="v-align-middle semi-bold sorting_1">
                                                            <p>${inform.conclution}</p>
                                                        </td>
                                                        <td class="v-align-middle semi-bold sorting_1">
                                                            <p>${inform.ubication}</p>
                                                        </td>
                                                        <td class="v-align-middle">
                                                            <div class="row">
                                                                <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                    <security:authorize
                                                                            access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                                        <button class="btn btn-success" type="button"
                                                                                onclick="fillReportModal(this)"><i
                                                                                class="fa fa-pencil"></i>
                                                                        </button>
                                                                    </security:authorize>
                                                                    <a class="btn btn-success" data-toggle="tooltip"
                                                                       data-original-title="Deficiencias"
                                                                       onclick="redirectURL('deficiency/all?id=${inform.id}')"><i
                                                                            class="fa fa-thumbs-o-down"></i>
                                                                    </a>
                                                                    <c:if test="${inform.documentMetadata!=null}">
                                                                        <a class="btn btn-success" data-toggle="tooltip"
                                                                           data-original-title="Descargar"
                                                                           onclick="downloadReport('${inform.documentMetadata.id}')"><i
                                                                                class="pg-download"></i>
                                                                        </a>
                                                                    </c:if>
                                                                    <security:authorize
                                                                            access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                                        <button class="btn btn-success" type="button"
                                                                                onclick="deleteReport(this)"><i
                                                                                class="fa fa-trash-o"></i>
                                                                        </button>
                                                                    </security:authorize>
                                                                </div>
                                                            </div>
                                                            <input type="text" hidden="true"
                                                                   value="${inform.id}"/>
                                                            <input type="text" hidden="true"
                                                                   value="${inform.controlAction.id}"/>
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
                <!-- END REPORTS PANELS -->
            </div>

            <!-- MODAL REPORT ADD UP  -->
            <div class="modal fade stick-up" id="addReportModal" tabindex="-1" role="dialog"
                 aria-labelledby="addReportModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanReportModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nuevo</span> Informe
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
                            <form role="form" method="POST" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Título</label>
                                            <input id="title" name="title" type="text" class="form-control"
                                                   placeholder="Nombre o título del informe" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Ubicación</label>
                                            <input id="ubication" name="ubication" type="text" class="form-control"
                                                   placeholder="Ubicación física del informe">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Conclusiones</label>
                                            <textarea id="conclution" name="conclution"
                                                      placeholder="Conclusiones" rows="2"
                                                      class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
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
                                <button id="add-report" type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateReportFieldsModal()) {
                               addReport();}return false;">Aceptar
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
            <!-- END MODAL REPORT ADD UP  -->

            <!-- MODAL REPORT EDIT UP  -->
            <div class="modal fade stick-up" id="editReportModal" tabindex="-1" role="dialog"
                 aria-labelledby="editReportModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Informe
                                <div class="pull-right hidden" id="progress-bar-edit">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="editModalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Título</label>
                                            <input id="editTitle" name="editTitle" type="text" class="form-control"
                                                   placeholder="Nombre o título del informe" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Ubicación</label>
                                            <input id="editUbication" name="editUbication" type="text"
                                                   class="form-control"
                                                   placeholder="Ubicación física del informe">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Conclusiones</label>
                                            <textarea id="editConclution" name="editConclution"
                                                      placeholder="Conclusiones" rows="2"
                                                      class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <Span class="span-semi-bold">Cambiar Archivo</Span>
                                        <div id="editFileContainer" class="form-group form-group-default">
                                            <input type="file" id="editFile" name="file"
                                                   placeholder="Subir archivo para cambiar el antiguo"/>
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-report" type="button" class="btn btn-primary  btn-cons pull-right">
                                    Aceptar
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
            <!-- END MODAL REPORT EDIT UP  -->

        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/report/report.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/general_committee.js"
            type="text/javascript"></script>
    <!-- END JS -->
    <script>
        var tables = document.getElementsByName("tableWithSearch");
        for (var i = 0; i < tables.length; i++) {
            var id = tables[i].getAttribute("id");
            generalInitTable(id);
        }
    </script>

    <!-- BEGIN ID -->
    <input id="idActionControl" type="text" hidden="true" value=""/>
    <input id="idReport" type="text" hidden="true" value=""/>
    <!-- END ID -->
</div>
</body>
</html>
