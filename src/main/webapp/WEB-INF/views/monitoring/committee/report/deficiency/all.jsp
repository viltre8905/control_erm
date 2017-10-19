<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 43);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Deficiencias</title>
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
                        <div class="panel-title">Deficiencias
                        </div>
                        <div class="pull-right">
                            <security:authorize
                                    access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                <div class="col-xs-7">
                                    <input type="text" placeholder="Buscar" class="form-control pull-right"
                                           id="search-table">
                                </div>
                                <div class="btn-group pull-right col-xs-5">
                                    <button id="showmodalAdd" class="btn btn-primary" type="button">Adicionar</button>
                                </div>
                            </security:authorize>
                            <security:authorize
                                    access="hasRole('ROLE_COMMITTEE_MEMBER')">
                                <div class="col-xs-11">
                                    <input type="text" placeholder="Buscar" class="form-control pull-right"
                                           id="search-table">
                                </div>
                            </security:authorize>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div id="tableWithSearch_wrapper" class="dataTables_wrapper form-inline no-footer">
                            <div class="table-responsive">
                                <table id="tableWithSearch"
                                       class="table table-hover demo-table-search dataTable no-footer"
                                       role="grid" aria-describedby="tableWithSearch_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="sorting_asc" tabindex="0" aria-controls="tableWithSearch"
                                            rowspan="1"
                                            colspan="1" style="width: 80px;" aria-sort="ascending">Número
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 600px;">Descripción
                                        </th>
                                        <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                            style="width: 80px;">Acciones
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="deficiency" items="${report.deficiencies}">
                                        <tr role="row" class="odd">

                                            <td class="v-align-middle semi-bold sorting_1">
                                                <p>${deficiency.no}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${deficiency.body}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <div class="row">
                                                    <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                        <security:authorize
                                                                access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                            <button class="btn btn-success" type="button"
                                                                    onclick="fillDeficiencyModal(this)"><i
                                                                    class="fa fa-pencil"></i>
                                                            </button>
                                                        </security:authorize>
                                                        <button class="btn btn-success" data-toggle="tooltip"
                                                                data-original-title="Medidas"
                                                                onclick="redirectURL('activity/activities?id=${deficiency.id}')">
                                                            <i
                                                                    class="fa fa-history"></i>
                                                        </button>
                                                        <security:authorize
                                                                access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                            <button class="btn btn-success" type="button"
                                                                    onclick="deleteDeficiency(this)"><i
                                                                    class="fa fa-trash-o"></i>
                                                            </button>
                                                        </security:authorize>
                                                    </div>
                                                </div>
                                                <input type="text" hidden="true" value="${deficiency.id}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <button class="btn btn-tag btn-tag-light pull-right m-t-5" type="button"
                            onclick="redirectURL('${pageContext.request.contextPath}/monitoring/committee/report/all')">Volver
                    </button>
                </div>
                <!-- END PANEL -->
            </div>
            <!-- MODAL DEFICIENCY ADD UP  -->
            <div class="modal fade stick-up" id="addNewAppModal" tabindex="-1" role="dialog"
                 aria-labelledby="addNewAppModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanDeficiencyModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nueva</span> Deficiencia
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
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default required">
                                            <label>Número</label>
                                            <input id="number" name="number" type="text" class="form-control"
                                                   placeholder="Número de a deficiencia" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Descripción</label>
                                            <textarea id="description" name="description"
                                                      placeholder="Descripción de la deficiencia" rows="2"
                                                      class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="add-deficiency" type="submit"
                                        class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateDeficiencyFieldsModal()) {
                               addDeficiency();}return false;">Aceptar
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
            <!-- END MODAL DEFICIENCY ADD UP  -->

            <!-- MODAL DEFICIENCY EDIT UP  -->
            <div class="modal fade stick-up" id="editAppModalDeficiency" tabindex="-1" role="dialog"
                 aria-labelledby="editAppModalDeficiency" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Deficiencia
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
                                            <label>Número</label>
                                            <input id="editNumber" name="editNumber" type="text"
                                                   class="form-control"
                                                   placeholder="Número de a deficiencia" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Descripción</label>
                                            <textarea id="editDescription" name="editDescription"
                                                      placeholder="Descripción de la deficiencia" rows="2"
                                                      class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-deficiency" type="button"
                                        class="btn btn-primary  btn-cons pull-right">
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
            <!-- END MODAL DEFICIENCY EDIT UP  -->
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

    <!-- BEGIN ID -->
    <input id="idDeficiency" type="text" hidden="true" value=""/>
    <input id="idReport" type="text" hidden="true" value="${report.id}"/>
    <!-- END ID -->
</div>
</body>
</html>

