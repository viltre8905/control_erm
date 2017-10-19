<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 41);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Miembros del comité de prevención y control</title>
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
                        Miembros del Comité de prevención y control
                    </h3>
                </div>
                <p>A continuaci&oacute;n se detallan todos los miembros del comité de prevención y control. Además, de
                    ser secretario del comité podrá agregar nuevos miembros o bien eliminar algun miembro.
                </p>
                <br/>
                <!-- START PANEL -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">Miembros Actuales
                        </div>
                        <div class="pull-right">
                            <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                <div class="col-xs-7">
                                    <input type="text" placeholder="Buscar" class="form-control pull-right"
                                           id="search-table">
                                </div>
                                <div class="btn-group pull-right col-xs-5">
                                    <button id="showmodalAdd" class="btn btn-primary" type="button">Adicionar</button>
                                </div>
                            </security:authorize>
                            <security:authorize access="hasRole('ROLE_COMMITTEE_MEMBER')">
                                <div class="col-xs-11">
                                    <input type="text" placeholder="Buscar" class="form-control pull-right"
                                           id="search-table">
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
                                        <th class="sorting_asc" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 400px;" aria-sort="ascending">Nombre y Apellidos
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 80px;">Cargo
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 300px;">Correo
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 80px;">Secretario
                                        </th>
                                        <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                            <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                style="width: 80px;">Eliminar
                                            </th>
                                        </security:authorize>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="member" items="${members}">
                                        <tr role="row" class="odd">
                                            <td class="v-align-middle semi-bold sorting_1">
                                                <p>${member.firstName} ${member.lastName}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${member.ocupation.name}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${member.email}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <c:choose>
                                                    <c:when test='${member.hasRole("ROLE_SECRETARY_COMMITTEE")}'>
                                                        <p>Si</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p>No</p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                <td class="v-align-middle">
                                                    <div class="row">
                                                        <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                            <button class='btn btn-success <c:if test="${userLogged.id==member.id}">disabled</c:if>'
                                                                    type="button"
                                                                    onclick="deleteCommitteeMember(this)"><i
                                                                    class="fa fa-trash-o"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <input type="text" hidden="true" value="${member.id}"/>
                                                </td>
                                            </security:authorize>
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
                <!-- END PANEL -->
            </div>

            <!-- MODAL COMMMITEE  -->
            <div class="modal fade stick-up" id="addNewAppModal" tabindex="-1" role="dialog"
                 aria-labelledby="addNewAppModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanCommitteeModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nuevo Miembro</span> del Comité
                                <div class="pull-right hidden" id="progress-bar-committee">
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
                                        <div class="form-group form-group-default">
                                            <label id="memberLabel">Seleccione un miembro</label>
                                            <select id="member" name="Miembro"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#memberLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <c:forEach var="user" items="${users}">
                                                    <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input id="switchery" type="checkbox"
                                               data-init-plugin="switchery"/>
                                        <label for="switchery">Secretario del comité</label>
                                    </div>
                                    <button id="add-committee-member" type="button"
                                            class="btn btn-primary  btn-cons pull-right"
                                            onclick="if (validateCommitteeFieldsModal()) {
                               addCommitteeMember();}return false;">Aceptar
                                    </button>
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
            <!-- END MODAL COMMMITEE -->

        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/member/member.js"
            type="text/javascript"></script
    <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/general_committee.js"
            type="text/javascript"></script>
    <!-- END JS -->

    <!-- BEGIN ID -->
    <input id="idGuide" type="text" hidden="true" value=""/>
    <!-- END ID -->
</div>
</body>
</html>
