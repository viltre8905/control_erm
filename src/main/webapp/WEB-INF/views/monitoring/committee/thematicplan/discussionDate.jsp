<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 44);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Fecha de discusión</title>
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
                        <div class="panel-title">Fecha de discusiones
                        </div>
                        <div class="pull-right">
                            <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                <div class="col-xs-7">
                                    <input type="text" placeholder="Buscar" class="form-control pull-right"
                                           id="search-table">
                                </div>
                                <div class="btn-group pull-right col-xs-5">
                                    <button class="btn btn-primary" type="button"
                                            onclick="$('#addDiscussionDateModal').modal('show')">Adicionar
                                    </button>
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
                                            colspan="1" style="width: 400px;" aria-sort="ascending">Responsable
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 200px;">Fecha
                                        </th>
                                        <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                            <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                style="width: 80px;">Acciones
                                            </th>
                                        </security:authorize>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="discussionDate" items="${discussionDates}">
                                        <tr role="row" class="odd">
                                            <td class="v-align-middle semi-bold sorting_1">
                                                <p>${discussionDate.responsible.firstName} ${discussionDate.responsible.lastName}
                                                </p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${discussionDate.toShortDiscussionDate()}</p>
                                            </td>
                                            <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                <td class="v-align-middle">
                                                    <div class="row">
                                                        <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                            <button class="btn btn-success" type="button"
                                                                    onclick="fillDiscussionDateModal(this)"><i
                                                                    class="fa fa-pencil"></i>
                                                            </button>
                                                            <button class="btn btn-success" type="button"
                                                                    onclick="deleteDiscussionDate(this)"><i
                                                                    class="fa fa-trash-o"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <input type="text" hidden="true" value="${discussionDate.id}"/>
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
                    <button class="btn btn-tag btn-tag-light pull-right m-t-5" type="button"
                            onclick="redirectURL('${pageContext.request.contextPath}/monitoring/committee/thematic-plan/themes')">
                        Volver
                    </button>
                </div>
                <!-- END PANEL -->
            </div>

            <!-- MODAL DISCUSSION DATE ADD UP  -->
            <div class="modal fade stick-up" id="addDiscussionDateModal" tabindex="-1" role="dialog"
                 aria-labelledby="addDiscussionDateModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanDiscussionDateModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nueva</span> Fecha de Discusión
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
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default required">
                                            <label id="responsibleLabel">Responsable</label>
                                            <select id="responsible" name="responsible"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#responsibleLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <c:forEach var="responsible" items="${responsibles}">
                                                    <option value="${responsible.id}">${responsible.firstName} ${responsible.lastName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default input-group required">
                                            <label>Fecha de discusión</label>
                                            <input type="email" class="date1 form-control"
                                                   placeholder="Escoge una fecha"
                                                   id="date">
                                            <span class="input-group-addon">
                                             <i class="fa fa-calendar"></i>
                                             </span>
                                        </div>
                                    </div>
                                </div>
                                <button id="add-discussion-date" type="submit"
                                        class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateDiscussionDateFieldsModal()) {
                               addDiscussionDate();}return false;">Aceptar
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
            <!-- END MODAL DISCUSSION DATE ADD UP  -->

            <!-- MODAL DISCUSSION DATE EDIT UP  -->
            <div class="modal fade stick-up" id="editDiscussionDateModal" tabindex="-1" role="dialog"
                 aria-labelledby="editDiscussionDateModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Fecha de Discusión
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
                                <div class="col-sm-6">
                                    <div class="form-group form-group-default required">
                                        <label id="editResponsibleLabel">Responsable</label>
                                        <select id="editResponsible" name="editResponsible"
                                                tabindex="-1" title=""
                                                style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                onblur="$('#editResponsibleLabel').removeAttr('class');">
                                            <option value="-1" selected="selected">Ninguno</option>
                                            <c:forEach var="responsible" items="${responsibles}">
                                                <option value="${responsible.id}">${responsible.firstName} ${responsible.lastName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group form-group-default input-group required">
                                        <label>Fecha de discusión</label>
                                        <input type="email" class="date1 form-control"
                                               placeholder="Escoge una fecha"
                                               id="editDate">
                                            <span class="input-group-addon">
                                             <i class="fa fa-calendar"></i>
                                             </span>
                                    </div>
                                </div>
                                <button id="edit-discussion-date" type="button" class="btn btn-primary  btn-cons pull-right">
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
            <!-- END MODAL DISCUSSION DATE EDIT UP  -->

        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/thematicplan/theme.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/general_committee.js"
            type="text/javascript"></script>
    <!-- END JS -->

    <!-- BEGIN ID -->
    <input id="themeId" type="text" hidden="true" value="${themeId}"/>
    <input id="discussionDateId" type="text" hidden="true" value=""/>
    <!-- END ID -->
</div>
</body>
</html>


