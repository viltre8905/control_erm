<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 44);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Plan Temático</title>
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
                        Plan Temático
                    </h3>
                </div>
                <p>Imprimir Plan: <a href="#" id="exportReport"><img alt="" class="icon-pdf"
                                                                     data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                     data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                     src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                     width="25" height="25">
                </a></p>
                <div class="progress-bar-container" id="progress-bar-export-report">
                    <div class="col-sm-1 text-center center-scale">
                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                        </div>
                    </div>
                </div>
                <br/>
                <!-- START PANEL -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">Temas
                        </div>
                        <div class="pull-right">
                            <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                <div class="col-xs-7">
                                    <input type="text" placeholder="Buscar" class="form-control pull-right"
                                           id="search-table">
                                </div>
                                <div class="btn-group pull-right col-xs-5">
                                    <button class="btn btn-primary" type="button"
                                            onclick="$('#addThemeModal').modal('show')">Adicionar
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
                                            colspan="1" style="width: 80px;" aria-sort="ascending">No
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 600px;">Tema
                                        </th>
                                        <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                            <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                style="width: 80px;">Acciones
                                            </th>
                                        </security:authorize>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="theme" items="${themes}">
                                        <tr role="row" class="odd">
                                            <td class="v-align-middle semi-bold sorting_1">
                                                <p>${theme.no}
                                                </p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${theme.theme}</p>
                                            </td>
                                            <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                <td class="v-align-middle">
                                                    <div class="row">
                                                        <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                            <button class="btn btn-success" type="button"
                                                                    onclick="fillThemeModal(this)"><i
                                                                    class="fa fa-pencil"></i>
                                                            </button>
                                                            <a class="btn btn-success" data-toggle="tooltip"
                                                               data-original-title="Fechas de discusión"
                                                               href="themes/discussionDate?id=${theme.id}"><i
                                                                    class="fa fa-calendar"></i>
                                                            </a>
                                                            <button class="btn btn-success" type="button"
                                                                    onclick="deleteTheme(this)"><i
                                                                    class="fa fa-trash-o"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <input type="text" hidden="true" value="${theme.id}"/>
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

            <!-- MODAL THEME ADD UP  -->
            <div class="modal fade stick-up" id="addThemeModal" tabindex="-1" role="dialog"
                 aria-labelledby="addThemeModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanThemeModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nuevo</span> Tema
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
                                            <label>No</label>
                                            <input id="no" name="no" type="text" class="form-control"
                                                   placeholder="Número del tema" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Tema</label>
                                            <textarea id="description" name="description"
                                                      placeholder="Descripción del tema" rows="2"
                                                      class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="add-theme" type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateThemeFieldsModal()) {
                               addTheme();}return false;">Aceptar
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
            <!-- END MODAL THEME ADD UP  -->

            <!-- MODAL THEME EDIT UP  -->
            <div class="modal fade stick-up" id="editThemeModal" tabindex="-1" role="dialog"
                 aria-labelledby="editThemeModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Tema
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
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default required">
                                            <label>No</label>
                                            <input id="editNo" name="editNo" type="text" class="form-control"
                                                   placeholder="Número del tema" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Tema</label>
                                            <textarea id="editDescription" name="editDescription"
                                                      placeholder="Descripción del tema" rows="2"
                                                      class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-theme" type="button" class="btn btn-primary  btn-cons pull-right">
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
            <!-- END MODAL THEME EDIT UP  -->

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
    <input id="themeId" type="text" hidden="true" value=""/>
    <!-- END ID -->
</div>
</body>
</html>

