<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 5);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administración de usuarios</title>
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
                        <div class="panel-title">Listado de Usuarios
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
                                            colspan="1" style="width: 100px;" aria-sort="ascending">Usuario
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 147px;">Nombre
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 147px;">Correo
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 141px;">Grupo
                                        </th>
                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                            colspan="1"
                                            style="width: 80px;">Activo
                                        </th>
                                        <security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                                            <th class="sorting" tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                                colspan="1"
                                                style="width: 147px;">Entidad
                                            </th>
                                        </security:authorize>
                                        <th tabindex="0" aria-controls="tableWithSearch" rowspan="1" colspan="1"
                                            style="width: 80px;">Acciones
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${users}">
                                        <tr role="row" class="odd">

                                            <td class="v-align-middle semi-bold sorting_1">
                                                <p>${user.userName}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${user.firstName}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <p>${user.email}</p>
                                            </td>
                                            <td class="v-align-middle">
                                                <c:forEach var="rol" items="${user.roles}">
                                                    <p>${rol.name}</p>
                                                </c:forEach>
                                            </td>
                                            <td class="v-align-middle">
                                                <c:choose>
                                                    <c:when test="${user.enabled}">
                                                        <input id="switchery${user.id}" type="checkbox"
                                                               data-init-plugin="switchery"
                                                               checked="checked"
                                                               onchange="userStatus(this)"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input id="switchery${user.id}" type="checkbox"
                                                               data-init-plugin="switchery"
                                                               onchange="userStatus(this)"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                                                <td class="v-align-middle">
                                                    <p>${user.entity.name}</p>
                                                </td>
                                            </security:authorize>
                                            <td class="v-align-middle">
                                                <div class="row">
                                                    <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                        <button class="btn btn-success" type="button"
                                                                onclick="fillUserModal(this)"><i
                                                                class="fa fa-pencil"></i>
                                                        </button>
                                                        <button class="btn btn-success" type="button"
                                                                onclick="deleteUser(this)"><i class="fa fa-trash-o"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                                <input type="text" hidden="true" value="${user.id}"/>
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

            <!-- MODAL USER ADD UP  -->
            <div class="modal fade stick-up" id="addNewAppModal" tabindex="-1" role="dialog"
                 aria-labelledby="addNewAppModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                    onclick="cleanUserModal()"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Nuevo</span> Usuario
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
                                                   placeholder="Nombre de la persona" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default required">
                                            <label>Apellidos</label>
                                            <input id="lastName" name="lastName" type="text" class="form-control"
                                                   placeholder="Apellidos de la persona" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>Correo</label>
                                            <input id="email" name="email" type="text" class="form-control"
                                                   placeholder="example@gmail.com" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>Identificación</label>
                                            <input id="identification" name="identification" type="text"
                                                   class="form-control"
                                                   placeholder="Cédula o Carnet de Identificación" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label id="ocupationLabel">Cargo</label>
                                            <select id="ocupation" name="ocupation"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#ocupationLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <c:forEach var="ocupation" items="${ocupations}">
                                                    <option value="${ocupation.id}">${ocupation.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <div class="fileinput fileinput-new" data-provides="fileinput"
                                                 id="photoDiv">
                                                <div class="fileinput-new thumbnail"
                                                     style="width: 250px; height: 160px;">
                                                    <img src=""
                                                         alt="">
                                                </div>
                                                <div class="fileinput-preview fileinput-exists thumbnail"
                                                     style="max-width: 250px; max-height: 160px;" id="photo">
                                                </div>
                                                <div>
                                            <span class="btn default btn-file">
                                            <span class="fileinput-new">Seleccionar Imagen </span>
											<span class="fileinput-exists">Cambiar </span>
											<input name="..." type="file" class=""></span>
                                                    <a href="#" class="btn default fileinput-exists"
                                                       data-dismiss="fileinput">
                                                        Eliminar </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group" id="checkbox_roles">
                                            <c:forEach var="rol_user" items="${rolesUsers}">
                                                <div class="checkbox check-success checkbox-circle">
                                                    <input type="checkbox" id="checkbox_roles${rol_user.id}"
                                                           name="checkbox_roles${rol_user.id}" value="${rol_user.id}">
                                                    <label
                                                            for="checkbox_roles${rol_user.id}">${rol_user.name}</label>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                                            <div class="row">
                                                <div class="form-group form-group-default">
                                                    <label id="entityLabel">Entidad</label>
                                                    <select id="entity" name="entity"
                                                            tabindex="-1" title=""
                                                            style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                            onblur="$('#entityLabel').removeAttr('class');">
                                                        <option value="-1" selected="selected">Ninguna</option>
                                                        <c:forEach var="entity" items="${entities}">
                                                            <option value="${entity.id}">${entity.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </security:authorize>
                                        <div class="row">
                                            <div class="form-group form-group-default required">
                                                <label>Usuario</label>
                                                <input id="username" name="username" type="text" class="form-control"
                                                       placeholder="Nombre del usuario" required="true">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group form-group-default input-group required">
                                                <label>Contraseña</label>
                                                <input id="password" name="password" type="password"
                                                       class="form-control"
                                                       placeholder="Contraseña del usuario" required="true"
                                                       onblur="validatePassword(this)" onkeyup="validatePassword(this)">
                                                <span class="input-group-addon text-danger">
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button id="buttonAdd" type="submit" class="btn btn-primary  btn-cons pull-right"
                                        onclick="if (validateUserFieldsModal('checkbox_roles')) {
                               addUser();}return false;">Aceptar
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
            <!-- END MODAL USER ADD UP  -->

            <!-- MODAL USER EDIT UP  -->
            <div class="modal fade stick-up" id="editAppModalUser" tabindex="-1" role="dialog"
                 aria-labelledby="editAppModalUser" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header clearfix ">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                                    class="pg-close fs-14"></i>
                            </button>
                            <h4 class="p-b-5"><span class="semi-bold">Modificar</span> Usuario: <span
                                    id="userNameTitle"></span>
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
                                                   placeholder="Nombre de la persona" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default required">
                                            <label>Apellidos</label>
                                            <input id="editLastName" name="editLastName" type="text"
                                                   class="form-control"
                                                   placeholder="Apellidos de la persona" required="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>Correo</label>
                                            <input id="editEmail" name="editEmail" type="text" class="form-control"
                                                   placeholder="example@gmail.com" required="true">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>Identificación</label>
                                            <input id="editIdentification" name="editIdentification" type="text"
                                                   class="form-control"
                                                   placeholder="Cédula o Carnet de Identificación" required="true">
                                        </div>
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label id="editOcupationLabel">Cargo</label>
                                            <select id="editOcupation" name="editOcupation"
                                                    tabindex="-1" title=""
                                                    style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                    onblur="$('#editOcupationLabel').removeAttr('class');">
                                                <option value="-1" selected="selected">Ninguno</option>
                                                <c:forEach var="ocupation" items="${ocupations}">
                                                    <option value="${ocupation.id}">${ocupation.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <div class="fileinput fileinput-new" data-provides="fileinput"
                                                 id="editPhotoDiv">
                                                <div class="fileinput-new thumbnail"
                                                     style="width: 250px; height: 160px;">
                                                    <img src=""
                                                         alt="">
                                                </div>
                                                <div class="fileinput-preview fileinput-exists thumbnail"
                                                     style="max-width: 250px; max-height: 160px;" id="editPhoto">
                                                </div>
                                                <div>
                                            <span class="btn default btn-file">
                                            <span class="fileinput-new">Seleccionar Imagen </span>
											<span class="fileinput-exists">Cambiar </span>
											<input name="..." type="file" class=""></span>
                                                    <a href="#" class="btn default fileinput-exists"
                                                       data-dismiss="fileinput">
                                                        Eliminar </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group" id="edit_checkbox_roles">
                                            <c:forEach var="rol_user" items="${rolesUsers}">
                                                <div class="checkbox check-success checkbox-circle">
                                                    <input type="checkbox" id="edit_checkbox_roles${rol_user.id}"
                                                           name="edit_checkbox_roles${rol_user.id}"
                                                           value="${rol_user.id}">
                                                    <label
                                                            for="edit_checkbox_roles${rol_user.id}">${rol_user.name}</label>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                                            <div class="row">
                                                <div class="form-group form-group-default">
                                                    <label id="editEntityLabel">Entidad</label>
                                                    <select id="editEntity" name="editEntity"
                                                            tabindex="-1" title=""
                                                            style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                                            onblur="$('#editEntityLabel').removeAttr('class');">
                                                        <option value="-1" selected="selected">Ninguna</option>
                                                        <c:forEach var="entity" items="${entities}">
                                                            <option value="${entity.id}">${entity.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </security:authorize>
                                        <div class="row">
                                            <div class="form-group form-group-default required">
                                                <label>Usuario</label>
                                                <input id="editUsername" name="editUsername" type="text"
                                                       class="form-control"
                                                       placeholder="Nombre del usuario" required="true">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group form-group-default input-group">
                                                <label>Contraseña</label>
                                                <input id="editPassword" name="editPassword" type="password"
                                                       class="form-control"
                                                       placeholder="Editar para cambiar Contraseña" required="true"
                                                       onblur="validatePassword(this)" onkeyup="validatePassword(this)">
                                             <span class="input-group-addon text-danger">
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button id="edit-user" type="button" class="btn btn-primary  btn-cons pull-right">
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
            <!-- END MODAL USER EDIT UP  -->
        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/admin/user/user.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/admin/general_admin.js"
            type="text/javascript"></script>
    <!-- END JS -->

    <!-- BEGIN ID -->
    <input id="idUser" type="text" hidden="true" value=""/>
    <!-- END ID -->
</div>
</body>
</html>
