<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 5);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administración de Preguntas</title>
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
                <!-- ASPECT PANEL -->
                <div id="aspectPanel" class="panel panel-default m-b-5">
                    <div class="panel-heading">
                        <div class="panel-title">Lista de Preguntas del Cuestionario<i class="pg-arrow_right"></i><span
                                style="color: #6d5cae;">${guideName}</span>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="windows">
                            <h5>
                                Aspectos
                            </h5>
                        </div>
                        <p>Las preguntas estan grupadas por aspectos. Seleccione un aspecto o añada uno nuevo
                        <p>
                        <form role="form">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group ">
                                        <select id="aspectSelect" class="full-width" data-init-plugin="select2"
                                                onchange="onAspectChange()">
                                            <option value="-1" selected="selected">Ninguno</option>
                                            <c:forEach var="aspect" items="${aspects}">
                                                <option value="${aspect.id}">${aspect.no}- ${aspect.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <button class="btn btn-primary" type="button"
                                            onclick=" $('#addNewAppModalAspect').modal('show');"><i
                                            class="pg-plus"></i>
                                    </button>
                                    <button id="aspectEditButton" class="btn btn-primary disabled" type="button"
                                            onclick="fillAspectModal()"><i
                                            class="fa fa-pencil"></i>
                                    </button>
                                    <button id="aspectDeleteButton" class="btn btn-primary disabled" type="button"
                                            onclick="deleteAspect()"><i
                                            class="fa fa-trash-o"></i>
                                    </button>
                                    <button id="aspectUploadButton" class="btn btn-primary" type="button"
                                            onclick="$('#uploadAppModalAspect').modal('show')"><i
                                            class="fa fa-upload"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- END ASPECT PANEL -->
                <!-- START PANEL -->
                <div id="questionPanel" class="panel panel-default hidden">
                    <div class="panel-heading">
                        <div class="panel-title">Preguntas
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
                                            colspan="1" style="width: 80px" aria-sort="ascending">Código
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 250px">Título
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 300px">Descripción
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1" style="width: 80px">Procedimiento
                                        </th>
                                        <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                            style="width: 80px">Acciones
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody id="tbodyQuestion">

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
                <button class="btn btn-tag btn-tag-light pull-right" type="button"
                        onclick="redirectURL('${pageContext.request.contextPath}/admin/guide/guides')">
                    Volver
                </button>
            </div>

            <!-- MODAL ASPECT ADD UP  -->
            <div class="modal fade stick-up" id="addNewAppModalAspect" tabindex="-1" role="dialog"
                 aria-labelledby="addNewAppModalAspect" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanAspectModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nuevo</span> Aspecto
                                <div class="pull-right hidden" id="progress-bar-add-aspect">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="aspectModalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST" action="create">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="form-group form-group-default required">
                                            <label>No</label>
                                            <input id="aspectNumber" name="aspectNumber" type="text"
                                                   class="form-control"
                                                   placeholder="Número" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-9">
                                        <div class="form-group form-group-default required">
                                            <label>Nombre</label>
                                            <input id="aspectName" name="aspectName" type="text" class="form-control"
                                                   placeholder="Nombre o título del aspecto" required="true">
                                        </div>
                                    </div>
                                </div>
                                <button id="add-aspect" type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateAspect()) {
                                        addAspect();}return false;">Aceptar
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
            <!-- END MODAL ASPECT ADD UP  -->
            <!-- MODAL ASPECT EDIT UP  -->
            <div class="modal fade stick-up" id="editAppModalAspect" tabindex="-1" role="dialog"
                 aria-labelledby="editAppModalAspect" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Aspecto: <span
                                    id="aspectTitle"></span>
                                <div class="pull-right hidden" id="progress-bar-edit-aspect">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="editAspectModalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST" action="create">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="form-group form-group-default required">
                                            <label>No</label>
                                            <input id="editAspectNumber" name="editAspectNumber" type="text"
                                                   class="form-control"
                                                   placeholder="Número" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-9">
                                        <div class="form-group form-group-default required">
                                            <label>Nombre</label>
                                            <input id="editAspectName" name="editAspectName" type="text"
                                                   class="form-control"
                                                   placeholder="Nombre o título del aspecto" required="true">
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-aspect" type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateAspect(true)) {
                                        editAspect();}return false;">Aceptar
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
            <!-- END MODAL ASPECT EDIT UP  -->
            <!-- MODAL ASPECT UPLOAD  -->
            <div class="modal fade stick-up" id="uploadAppModalAspect" tabindex="-1" role="dialog"
                 aria-labelledby="uploadAppModalAspect" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanAspectUploadModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Cargar</span> Aspectos
                                <div class="pull-right hidden" id="progress-bar-upload-aspect">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="uploadAspectModalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST" action="create">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default required">
                                            <label id="uploadLabel">Seleccione el cuestionario de donde desea importar
                                                los aspectos</label>
                                            <select id="guides" name="guides"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#uploadLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <c:forEach var="guide" items="${guides}">
                                                    <option value="${guide.id}">${guide.name} (${guide.process.name})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <button id="upload-aspect" type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateUploadAspect()) {
                                        uploadAspect();}return false;">Aceptar
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
            <!-- END MODAL ASPECT UPLOAD  -->

            <!-- MODAL QUESTION ADD UP  -->
            <div class="modal fade stick-up" id="addNewAppModal" tabindex="-1" role="dialog"
                 aria-labelledby="addNewAppModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanQuestionModal();"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nueva</span> Pregunta
                                <div class="pull-right hidden" id="progress-bar-add-question">
                                    <div class="col-sm-1 text-center center-scale">
                                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                        </div>
                                    </div>
                                </div>
                            </h4>
                            <div id="modalMessage"></div>
                        </div>
                        <div class="modal-body">
                            <form role="form" method="POST" action="create">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="form-group form-group-default required">
                                            <label>Código</label>
                                            <input id="code" name="code" type="text" class="form-control"
                                                   placeholder="Código" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-9">
                                        <div class="form-group form-group-default required">
                                            <label>Título</label>
                                            <input id="title" name="title" type="text" class="form-control"
                                                   placeholder="Título de la pregunta" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4">
                                        <input id="switchery" type="checkbox"
                                               data-init-plugin="switchery"/>
                                        <label for="switchery">Procedimiento</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <div class="form-group form-group-default">
                                            <label>Descripción</label>
                                        <textarea id="description" name="description"
                                                  placeholder="Descripción de la pregunta (Opcional)"
                                                  rows="2"
                                                  class="full-width"
                                                  style="border: 0px;"
                                                  onfocus="$(this).attr('class','full-width focused')"
                                                  onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="add-question" type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateQuestionFieldsModal()) {
                                        addQuestion();}return false;">Aceptar
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
            <!-- END MODAL QUESTION ADD UP  -->

            <!-- MODAL QUESTION EDIT UP  -->
            <div class="modal fade stick-up" id="editAppModalQuestion" tabindex="-1" role="dialog"
                 aria-labelledby="editAppModalQuestion" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Pregunta
                                <div class="pull-right hidden" id="progress-bar-edit-question">
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
                                    <div class="col-sm-3">
                                        <div class="form-group form-group-default required">
                                            <label>Código</label>
                                            <input id="editCode" name="editCode" type="text" class="form-control"
                                                   placeholder="Código" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-9">
                                        <div class="form-group form-group-default required">
                                            <label>Título</label>
                                            <input id="editTitle" name="editTitle" type="text" class="form-control"
                                                   placeholder="Título de la pregunta" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4">
                                        <input id="editSwitchery" type="checkbox"
                                               data-init-plugin="switchery"/>
                                        <label for="editSwitchery">Procedimiento</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <div class="form-group form-group-default">
                                            <label>Descripción</label>
                                            <textarea id="editDescription" name="editDescription"
                                                      placeholder="Descripción de la pregunta (Opcional)" rows="2"
                                                      class="full-width"
                                                      style="border: 0px;"
                                                      onfocus="$(this).attr('class','full-width focused')"
                                                      onblur="$(this).attr('class','full-width')"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-question" type="button" class="btn btn-primary  btn-cons pull-right">
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
            <!-- END MODAL QUESTION EDIT UP  -->

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
                            </h4>
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
    <script src="${pageContext.request.contextPath}/resources/js/admin/guide/guide.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/admin/general_admin.js"
            type="text/javascript"></script>
    <!-- END JS -->
</div>

<!-- BEGIN ID -->
<input id="idGuide" type="text" hidden="true" value="${idGuide}"/>
<input id="idQuestion" type="text" hidden="true" value=""/>
<script>
    onAspectChange();
</script>
<!-- END ID -->
</div>
</body>
</html>
