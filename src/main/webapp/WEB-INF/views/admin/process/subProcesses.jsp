<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 4);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administración de Sub-procesos</title>
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
                        <div class="panel-title">Sub-Procesos del Proceso <i class="pg-arrow_right"></i><span
                                style="color: #6d5cae;">${process.name}</span>
                        </div>
                        <div class="pull-right">
                            <div class="col-xs-7">
                                <input type="text" placeholder="Buscar" class="form-control pull-right"
                                       id="search-table">
                            </div>
                            <div class="btn-group pull-right col-xs-5">
                                <button id="showmodalAdd" class="btn btn-primary" type="button">Adicionar</button>
                            </div>
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
                                        <th class="sorting_asc" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 147px;">Nombre
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 141px;">Descripción
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 141px;">Responsable
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 141px;">Miembros
                                        </th>

                                        <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                            style="width: 80px;">Acciones
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="subProcess" items="${subProcesses}">
                                        <tr role="row" class="odd">

                                            <td class="v-align-middle semi-bold sorting_1">
                                                <p>${subProcess.name}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${subProcess.description}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${subProcess.responsible.firstName} ${subProcess.responsible.lastName}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <select id="members" onchange="setMembers(this,true);"
                                                        class=" full-width"
                                                        data-init-plugin="select2" multiple>
                                                    <c:forEach var="memberUser" items="${process.members}">
                                                        <c:if test='${!memberUser.hasRole("ROLE_SUBPROCESS_SUPERVISORY")}'>
                                                            <c:choose>
                                                                <c:when test="${subProcess.members.contains(memberUser)}">
                                                                    <option value="${memberUser.id}"
                                                                            selected="selected">${memberUser.firstName} ${memberUser.lastName}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${memberUser.id!=subProcess.responsible.id}">
                                                                        <option value="${memberUser.id}">${memberUser.firstName} ${memberUser.lastName}</option>
                                                                    </c:if>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if>
                                                    </c:forEach>

                                                </select>
                                            </td>
                                            <td class="v-align-middle">
                                                <div class="row">
                                                    <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                        <button class="btn btn-success" type="button"
                                                                data-toggle="tooltip"
                                                                data-original-title="Editar Proceso"
                                                                onclick="fillProcessModal(this,true)"><i
                                                                class="fa fa-pencil"></i>
                                                        </button>
                                                        <a class="btn btn-success" data-toggle="tooltip"
                                                           data-original-title="Ver Actividades"
                                                           href="${pageContext.request.contextPath}/admin/process/activity/activities?id=${subProcess.id}&processId=${process.id}"><i
                                                                class="fa fa-table"></i>
                                                        </a>
                                                        <button class="btn btn-success" type="button"
                                                                data-toggle="tooltip"
                                                                data-original-title="Eliminar"
                                                                onclick="deleteProcess(this)"><i
                                                                class="fa fa-trash-o"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                                <input type="text" hidden="true" value="${subProcess.id}"/>
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
                            onclick="redirectURL('${pageContext.request.contextPath}/admin/process/processes')">
                        Volver
                    </button>
                </div>
                <!-- END PANEL -->
            </div>

            <!-- MODAL PROCESS ADD UP  -->
            <div class="modal fade stick-up" id="addNewAppModal" tabindex="-1" role="dialog"
                 aria-labelledby="addNewAppModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanProcessModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nuevo</span> Sub-Proceso
                                <div class="pull-right hidden" id="progress-bar-add">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="modalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form id="formAddUser" role="form" method="POST" action="create">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default required">
                                            <label>Nombre</label>
                                            <input id="name" name="name" type="text" class="form-control"
                                                   placeholder="Nombre del sub-proceso" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label id="responsibleLabel">Responsable</label>
                                            <select id="responsible" name="responsible"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#responsibleLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <c:forEach var="user" items="${process.members}">
                                                    <c:if test='${user.hasRole("ROLE_SUBPROCESS_SUPERVISORY")}'>
                                                        <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Descripción</label>
                                    <textarea id="description" name="description"
                                              placeholder="Descripción del sub-proceso" rows="2" class="full-width"
                                              style="border: 0px;" onfocus="$(this).attr('class','full-width focused')"
                                              onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>

                                </div>
                                <button type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateProcess()) {
                               addProcess(true);}return false;">Aceptar
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
            <!-- END MODAL PROCESS ADD UP  -->

            <!-- MODAL PROCESS EDIT UP  -->
            <div class="modal fade stick-up" id="editAppModalProcess" tabindex="-1" role="dialog"
                 aria-labelledby="editAppModalProcess" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Sub-Proceso: <span
                                    id="processTitle"></span>
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
                            <form role="form" method="POST" action="">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default required">
                                            <label>Nombre</label>
                                            <input id="editName" name="editName" type="text" class="form-control"
                                                   placeholder="Nombre del proceso" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label id="editResponsibleLabel">Responsable</label>
                                            <select id="editResponsible" name="editResponsible"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#editResponsibleLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <c:forEach var="user" items="${process.members}">
                                                    <c:if test='${user.hasRole("ROLE_SUBPROCESS_SUPERVISORY")}'>
                                                        <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Descripción</label>
                                    <textarea id="editDescription" name="editDescription"
                                              placeholder="Descripción del proceso" rows="2" class="full-width"
                                              style="border: 0px;" onfocus="$(this).attr('class','full-width focused')"
                                              onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>

                                </div>
                                <button id="edit-process" type="button" class="btn btn-primary  btn-cons pull-right">
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
            <!-- END MODAL PROCESS EDIT UP  -->
        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN ID -->
    <input id="idSubProccess" type="text" hidden="true" value=""/>
    <input id="idProcess" type="text" hidden="true" value="${process.id}"/>
    <!-- END ID -->
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/admin/process/process.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/admin/general_admin.js"
            type="text/javascript"></script>
</div>
</body>
</html>
