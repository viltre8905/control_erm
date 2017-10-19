<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 6);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administración de nomencladores</title>
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
                <div class="row">
                    <div class="col-md-6">
                        <!-- START PANEL -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-title">Acciones de Control
                                </div>
                                <div class="pull-right">
                                    <div class="btn-group pull-right col-xs-11">
                                        <button id="showmodalAdd1" class="btn btn-primary" type="button">Adicionar
                                        </button>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="panel-body">
                                <div id="tableWithSearch_wrapper1" class="dataTables_wrapper form-inline no-footer">
                                    <div class="table-responsive">
                                        <table id="tableWithSearch1"
                                               class="table table-hover demo-table-search dataTable no-footer"
                                               role="grid" aria-describedby="tableWithSearch_info">
                                            <thead>
                                            <tr role="row">
                                                <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                    rowspan="1"
                                                    colspan="1"
                                                    style="width: 147px;">Nombre
                                                </th>
                                                <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                    style="width: 80px;">Acciones
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="controlAction" items="${controlActions}">
                                                <tr role="row" class="odd">
                                                    <td class="v-align-middle semi-bold sorting_1">
                                                        <p>${controlAction.name}</p>
                                                    </td>
                                                    <td class="v-align-middle">
                                                        <div class="row">
                                                            <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                <button class="btn btn-success" type="button"
                                                                        onclick="fillNomenclatureModal(this,1)"><i
                                                                        class="fa fa-pencil"></i>
                                                                </button>
                                                                <button class="btn btn-success" type="button"
                                                                        onclick="deleteNomenclature(this,1)"><i
                                                                        class="fa fa-trash-o"></i>
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <input type="text" hidden="true" value="${controlAction.id}"/>
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
                        <!-- END PANEL -->
                    </div>
                    <div class="col-md-6">
                        <!-- START PANEL -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-title">Procedencias de los Documentos
                                </div>
                                <div class="pull-right">
                                    <div class="btn-group pull-right col-xs-11">
                                        <button id="showmodalAdd2" class="btn btn-primary" type="button">Adicionar
                                        </button>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="panel-body">
                                <div id="tableWithSearch_wrapper2" class="dataTables_wrapper form-inline no-footer">
                                    <div class="table-responsive">
                                        <table id="tableWithSearch2"
                                               class="table table-hover demo-table-search dataTable no-footer"
                                               role="grid" aria-describedby="tableWithSearch_info">
                                            <thead>
                                            <tr role="row">
                                                <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                    rowspan="1"
                                                    colspan="1"
                                                    style="width: 147px;">Nombre
                                                </th>
                                                <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                    style="width: 80px;">Acciones
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="documentProcedence" items="${documentProcedences}">
                                                <tr role="row" class="odd">
                                                    <td class="v-align-middle semi-bold sorting_1">
                                                        <p>${documentProcedence.name}</p>
                                                    </td>
                                                    <td class="v-align-middle">
                                                        <div class="row">
                                                            <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                <button class="btn btn-success" type="button"
                                                                        onclick="fillNomenclatureModal(this,2)"><i
                                                                        class="fa fa-pencil"></i>
                                                                </button>
                                                                <button class="btn btn-success" type="button"
                                                                        onclick="deleteNomenclature(this,2)"><i
                                                                        class="fa fa-trash-o"></i>
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <input type="text" hidden="true"
                                                               value="${documentProcedence.id}"/>
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
                        <!-- END PANEL -->
                    </div>
                </div>
            </div>
            <br/>
            <div class="container-fluid container-fixed-lg">
                <div class="row">
                    <div class="col-md-6">
                        <!-- START PANEL -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-title">Tipos de documentos
                                </div>
                                <div class="pull-right">
                                    <div class="btn-group pull-right col-xs-11">
                                        <button id="showmodalAdd3" class="btn btn-primary" type="button">Adicionar
                                        </button>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="panel-body">
                                <div id="tableWithSearch_wrapper3" class="dataTables_wrapper form-inline no-footer">
                                    <div class="table-responsive">
                                        <table id="tableWithSearch3"
                                               class="table table-hover demo-table-search dataTable no-footer"
                                               role="grid" aria-describedby="tableWithSearch_info">
                                            <thead>
                                            <tr role="row">
                                                <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                    rowspan="1"
                                                    colspan="1"
                                                    style="width: 147px;">Nombre
                                                </th>
                                                <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                    style="width: 80px;">Acciones
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="documentType" items="${documentTypes}">
                                                <tr role="row" class="odd">
                                                    <td class="v-align-middle semi-bold sorting_1">
                                                        <p>${documentType.name}</p>
                                                    </td>
                                                    <td class="v-align-middle">
                                                        <div class="row">
                                                            <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                <button class="btn btn-success" type="button"
                                                                        onclick="fillNomenclatureModal(this,3)"><i
                                                                        class="fa fa-pencil"></i>
                                                                </button>
                                                                <button class="btn btn-success" type="button"
                                                                        onclick="deleteNomenclature(this,3)"><i
                                                                        class="fa fa-trash-o"></i>
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <input type="text" hidden="true" value="${documentType.id}"/>
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
                        <!-- END PANEL -->
                    </div>
                    <div class="col-md-6">
                        <!-- START PANEL -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-title">Cargos
                                </div>
                                <div class="pull-right">
                                    <div class="btn-group pull-right col-xs-11">
                                        <button id="showmodalAdd4" class="btn btn-primary" type="button">Adicionar
                                        </button>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="panel-body">
                                <div id="tableWithSearch_wrapper5" class="dataTables_wrapper form-inline no-footer">
                                    <div class="table-responsive">
                                        <table id="tableWithSearch5"
                                               class="table table-hover demo-table-search dataTable no-footer"
                                               role="grid" aria-describedby="tableWithSearch_info">
                                            <thead>
                                            <tr role="row">
                                                <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                    rowspan="1"
                                                    colspan="1"
                                                    style="width: 147px;">Nombre
                                                </th>
                                                <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                    style="width: 80px;">Acciones
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="ocupation" items="${ocupations}">
                                                <tr role="row" class="odd">
                                                    <td class="v-align-middle semi-bold sorting_1">
                                                        <p>${ocupation.name}</p>
                                                    </td>
                                                    <td class="v-align-middle">
                                                        <div class="row">
                                                            <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                <button class="btn btn-success" type="button"
                                                                        onclick="fillNomenclatureModal(this,4)"><i
                                                                        class="fa fa-pencil"></i>
                                                                </button>
                                                                <button class="btn btn-success" type="button"
                                                                        onclick="deleteNomenclature(this,4)"><i
                                                                        class="fa fa-trash-o"></i>
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <input type="text" hidden="true" value="${ocupation.id}"/>
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
                        <!-- END PANEL -->
                    </div>
                </div>
            </div>
            <br/>
            <div class="container-fluid container-fixed-lg">
                <div class="row">

                    <div class="col-md-6">
                        <!-- START PANEL -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-title">Eficacia
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
                                                <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                    rowspan="1"
                                                    colspan="1"
                                                    style="width: 147px;">Nombre
                                                </th>
                                                <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                    style="width: 80px;">Porcentaje
                                                </th>
                                                <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                                    style="width: 80px;">Acciones
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="nefficacy" items="${nefficacies}">
                                                <c:if test="${nefficacy.id!=3}">
                                                    <tr role="row" class="odd">
                                                        <td class="v-align-middle semi-bold sorting_1">
                                                            <p>${nefficacy.name}</p>
                                                        </td>
                                                        <td class="v-align-middle semi-bold sorting_1">
                                                            <p>${nefficacy.percent}%</p>
                                                        </td>
                                                        <td class="v-align-middle">
                                                            <div class="row">
                                                                <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                    <button class="btn btn-success" type="button"
                                                                            onclick="fillEfficacyModal(this)"><i
                                                                            class="fa fa-pencil"></i>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <input type="text" hidden="true" value="${nefficacy.id}"/>
                                                        </td>
                                                    </tr>
                                                </c:if>
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
                    <div class="col-md-6">
                    </div>
                </div>
            </div>

            <!-- MODAL NOMENCLATURE ADD UP  -->
            <div class="modal fade stick-up" id="addNewAppModalNomenclature" tabindex="-1" role="dialog"
                 aria-labelledby="addNewAppModalNomenclature" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanNomenclatureModal()"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5" id="NomenclatureModalTitle">
                            </h4>
                            <div id="modalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST" action="">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Nombre</label>
                                            <input id="name" name="name" type="text" class="form-control"
                                                   placeholder="Nombre del nomenclador" required="true">
                                        </div>
                                    </div>
                                </div>
                                <button id="add-app" type="button" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateNomenclatureFieldsModal(false)) {
                              addNomenclature();
                            }">Aceptar
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
            <!-- END MODAL NOMENCLATURE ADD UP  -->

            <!-- MODAL EFFICACY  -->
            <div class="modal fade stick-up" id="efficacyModal" tabindex="-1" role="dialog"
                 aria-labelledby="efficacyModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanEfficacyModal()"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5">Cambiar Porcentaje
                                <div class="pull-right hidden" id="progress-bar-edit-efficacy">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="efficacyModalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST" action="">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Porcentaje</label>
                                            <input id="efficacyPercent" name="efficacyPercent" type="text"
                                                   class="form-control"
                                                   placeholder="Porcentaje necesario para alcanzar la eficacia en cuestión"
                                                   required="true">
                                        </div>
                                    </div>
                                </div>
                                <button id="add-efficacy-button" type="button"
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
            <!-- END MODAL NOMENCL MODAL EFFICACY  -->

            <!-- MODAL NOMENCLATURE EDIT UP  -->
            <div class="modal fade stick-up" id="editAppModalNomenclature" tabindex="-1" role="dialog"
                 aria-labelledby="editAppModalNomenclature" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5" id="editNomenclatureModalTitle">
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
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label>Nombre</label>
                                            <input id="editName" name="editName" type="text" class="form-control"
                                                   placeholder="Nombre del nomenclador" required="true">
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-nomenclature" type="button"
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
            <!-- END MODAL NOMENCLATURE EDIT UP  -->
        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/admin/nomenclature/nomenclature.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/admin/general_admin.js"
            type="text/javascript"></script>
    <script>
        for (var i = 1; i <= 4; i++) {
            showModalNomenclature(i);
        }
    </script>
    <!-- END JS -->

    <!-- HIDDEN-->
    <input id="idNomenclature" type="text" hidden="true" value=""/>
    <input id="type" type="text" hidden="true" value=""/>
    <!-- END HIDDEN -->
</div>
</body>
</html>
