<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 3);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administración de Entidades</title>
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
                        <div class="panel-title">Entidades
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
                                            colspan="1" style="width: 150px;" aria-sort="ascending">Nombre
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 200px;">Misión
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 200px;">Visión
                                        </th>
                                        <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                            style="width: 80px;">Acciones
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="entity" items="${entities}">
                                        <tr role="row" class="odd">

                                            <td class="v-align-middle semi-bold sorting_1">
                                                <p>${entity.name}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${entity.mission}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${entity.vision}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <div class="row">
                                                    <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                        <button class="btn btn-success" type="button"
                                                                onclick="fillEntityModal(this)"><i
                                                                class="fa fa-pencil"></i>
                                                        </button>
                                                        <a class="btn btn-success" data-toggle="tooltip"
                                                           data-original-title="Objetivos Estratégicos"
                                                           href="strategicObjective/all?entityId=${entity.id}"><i
                                                                class="pg-ordered_list"></i>
                                                        </a>
                                                        <button class="btn btn-success" type="button"
                                                                onclick="deleteEntity(this)"><i
                                                                class="fa fa-trash-o"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                                <input type="text" hidden="true" value="${entity.id}"/>
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
            <!-- MODAL ENTITY ADD UP  -->
            <div class="modal fade stick-up" id="addNewAppModal" tabindex="-1" role="dialog"
                 aria-labelledby="addNewAppModal" aria-hidden="true">
                <div class="modal-dialog" style="width: 800px">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanEntityModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nueva</span> Entidad
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
                                            <label>Nombre</label>
                                            <input id="name" name="name" type="text" class="form-control"
                                                   placeholder="Nombre de la empresa" required="true">
                                        </div>
                                        <div class="form-group form-group-default">
                                            <label>Dirección</label>
                                            <input id="address" name="address" type="text" class="form-control"
                                                   placeholder="Dirección física" required="true">
                                        </div>
                                        <div class="form-group form-group-default">
                                            <label>Dirección WEB</label>
                                            <input id="webAddress" name="webAddress" type="text" class="form-control"
                                                   placeholder="Dirección web de a entidad" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <div class="fileinput fileinput-new" data-provides="fileinput"
                                                 id="photoDiv">
                                                <div class="fileinput-new thumbnail"
                                                     style="width: 250px; height: 140px;">
                                                    <img src=""
                                                         alt="">
                                                </div>
                                                <div class="fileinput-preview fileinput-exists thumbnail"
                                                     style="max-width: 250px; max-height: 140px;" id="photo">
                                                </div>
                                                <div>
                                            <span class="btn default btn-file">
                                            <span class="fileinput-new">Logotipo </span>
											<span class="fileinput-exists">Cambiar </span>
											<input name="..." type="file" class=""></span>
                                                    <a href="#" class="btn default fileinput-exists"
                                                       data-dismiss="fileinput">
                                                        Eliminar </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>Visión</label>
                                        <textarea id="vision" name="vision"
                                                  placeholder="Visión general de la empresa" rows="3" class="full-width"
                                                  style="border: 0px;"
                                                  onfocus="$(this).attr('class','full-width focused')"
                                                  onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>Misión</label>
                                        <textarea id="mission" name="mission"
                                                  placeholder="Misión de la entidad" rows="3" class="full-width"
                                                  style="border: 0px;"
                                                  onfocus="$(this).attr('class','full-width focused')"
                                                  onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="add-entity" type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateEntityFieldsModal()) {
                               addEntityData();}return false;">Aceptar
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
            <!-- END MODAL ENTITY ADD UP  -->

            <!-- MODAL GUIDE EDIT UP  -->
            <div class="modal fade stick-up" id="editAppModalEntity" tabindex="-1" role="dialog"
                 aria-labelledby="editAppModalEntity" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Entidad
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
                                                   placeholder="Nombre de la empresa" required="true">
                                        </div>
                                        <div class="form-group form-group-default">
                                            <label>Dirección</label>
                                            <input id="editAddress" name="editAddress" type="text" class="form-control"
                                                   placeholder="Dirección física" required="true">
                                        </div>
                                        <div class="form-group form-group-default">
                                            <label>Dirección WEB</label>
                                            <input id="editWebAddress" name="editWebAddress" type="text" class="form-control"
                                                   placeholder="Dirección web de a entidad" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <div class="fileinput fileinput-new" data-provides="fileinput"
                                                 id="editPhotoDiv">
                                                <div class="fileinput-new thumbnail"
                                                     style="width: 250px; height: 140px;">
                                                    <img src=""
                                                         alt="">
                                                </div>
                                                <div class="fileinput-preview fileinput-exists thumbnail"
                                                     style="max-width: 250px; max-height: 140px;" id="editPhoto">
                                                </div>
                                                <div>
                                            <span class="btn default btn-file">
                                            <span class="fileinput-new">Logotipo </span>
											<span class="fileinput-exists">Cambiar </span>
											<input name="..." type="file" class=""></span>
                                                    <a href="#" class="btn default fileinput-exists"
                                                       data-dismiss="fileinput">
                                                        Eliminar </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>Visión</label>
                                        <textarea id="editVision" name="editVision"
                                                  placeholder="Visión general de la empresa" rows="3" class="full-width"
                                                  style="border: 0px;"
                                                  onfocus="$(this).attr('class','full-width focused')"
                                                  onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>Misión</label>
                                        <textarea id="editMission" name="editMission"
                                                  placeholder="Misión de la entidad" rows="3" class="full-width"
                                                  style="border: 0px;"
                                                  onfocus="$(this).attr('class','full-width focused')"
                                                  onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-entity" type="button" class="btn btn-primary  btn-cons pull-right">
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
            <!-- END MODAL GUIDE EDIT UP  -->

        </div>

        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/admin/entity/entity.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/admin/general_admin.js"
            type="text/javascript"></script>

    <!-- BEGIN ID -->
    <input id="idEntity" type="text" hidden="true" value=""/>
    <!-- END ID -->
</div>
</body>
</html>
