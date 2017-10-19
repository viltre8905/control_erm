<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 9);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Control Interno</title>
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
                        <div class="panel-title">Procesos del Control Interno
                        </div>
                        <div class="pull-right">
                            <div class="col-xs-11">
                                <input type="text" placeholder="Buscar" class="form-control pull-right"
                                       id="search-table">
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
                                            colspan="1" style="width: 150px;" aria-sort="ascending">Proceso
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 300px;">Descripción
                                        </th>
                                        <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                            style="width: 80px;">Ejecutar
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr role="row" class="odd">
                                        <td class="v-align-middle semi-bold sorting_1">
                                            <p>Guía de Autocontrol</p>
                                        </td>
                                        <td class="v-align-middle">
                                            <p>Proceso que permite a la entidad responder la guía de autocontrol</p>
                                        </td>
                                        <td class="v-align-middle">
                                            <div class="row">
                                                <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                    <button id="buttonExecuteGuide" class="btn btn-success"
                                                            type="button"
                                                            onclick="executeGuide()"><i
                                                            class="fa fa-play"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr role="row" class="odd">
                                        <td class="v-align-middle semi-bold sorting_1">
                                            <p>Control de riesgos</p>
                                        </td>
                                        <td class="v-align-middle">
                                            <p>Proceso que permite a la entidad controlar los riesgos creando
                                                las medidas para el control de los mismos</p>
                                        </td>
                                        <td class="v-align-middle">
                                            <div class="row">
                                                <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                    <button id="buttonExecuteRisk" class="btn btn-success"
                                                            type="button"
                                                            onclick="executeRisk()"><i
                                                            class="fa fa-play"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr role="row" class="odd">
                                        <td class="v-align-middle semi-bold sorting_1">
                                            <p>Comité de Control</p>
                                        </td>
                                        <td class="v-align-middle">
                                            <p>Proceso que permite al comité de control ejecutar las auditorías y
                                                asignar las medidas correspondientes</p>
                                        </td>
                                        <td class="v-align-middle">
                                            <div class="row">
                                                <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                    <button id="buttonExecuteCommittee" class="btn btn-success"
                                                            type="button"
                                                            onclick="executeCommittee()"><i
                                                            class="fa fa-play"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
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
            <!-- MODAL ALERT  -->
            <div class="modal fade stick-up" id="alert-modal" tabindex="-1" role="dialog"
                 aria-labelledby="alert-modal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span id="alert-modal-title"></span>
                                <div class="pull-right hidden" id="progress-bar-alert">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="modalMessage"></div>
                        </div>
                        <div class="modal-body text-center">
                            <button id="alert-modal-button" type="button" class="btn btn-primary  btn-cons">Continuar
                            </button>
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- END MODAL ALERT-->
        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/admin/general_admin.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/admin/internalcontrol/internal_control.js"
            type="text/javascript"></script>
    <!-- END JS -->
</div>
</body>
</html>
