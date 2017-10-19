<%@page contentType="text/html" pageEncoding="UTF-8" %>
</div>
</div>
<div class=" pull-right">
    <div class="header-inner">
        <a class="icon-set menu-hambuger-plus m-l-20 sm-no-margin hidden-sm hidden-xs"
           data-toggle="quickview" data-toggle-element="#quickview"></a>
    </div>
</div>
<div class=" pull-right">
    <!-- START User Info-->
    <div class="visible-lg visible-md m-t-10">
        <div class="pull-left p-r-10 p-t-10 fs-16 font-heading">
            <span class="semi-bold">${userLogged.firstName}</span> <span
                class="text-master">${userLogged.lastName} (</span><a
                href="${pageContext.request.contextPath}/logout">salir</a><span class="text-master">)</span>
        </div>
        <c:if test="${userLogged.pathPhoto!=null}">
            <div class="thumbnail-wrapper d32 circular inline m-t-5">
                <img src="${userLogged.pathPhoto}" alt="" data-src="${userLogged.pathPhoto}"
                     data-src-retina="${userLogged.pathPhoto}" width="32" height="32">
            </div>
        </c:if>
    </div>
    <!-- END User Info-->
</div>
</div>
<!-- MODAL CHANGE PASSWORD -->
<div class="modal fade fill-in" id="changePasswordModal" tabindex="-1" role="dialog"
     aria-labelledby="changePasswordModal" aria-hidden="true">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
            onclick="cleanChangePasswordModal()"><i
            class="pg-close"></i>
    </button>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="windows">
                    <h5 class="text-left p-b-5"><span class="semi-bold">Cambiar</span> contrase&ntilde;a
                        <div class="pull-right hidden" id="progress-bar-password">
                            <div class="col-sm-1 text-center center-scale">
                                <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                </div>
                            </div>
                        </div>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <div class="row m-b-5">
                    <div class="col-sm-9">
                        <input id="oldPassword" name="oldPassword" type="password" class="form-control input-lg"
                               placeholder="Contrase&ntilde;a anterior" required="true">
                    </div>
                </div>
                <div class="row m-b-5">
                    <div class="col-sm-9">
                        <div class="input-group">
                            <input id="newPassword" name="newPassword" type="password" class="form-control input-lg"
                                   placeholder="Contrase&ntilde;a nueva" required="true"
                                   onblur="validatePassword(this)" onkeyup="validatePassword(this)">
                            <span class="input-group-addon text-danger">
                                                </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-9">
                        <input id="confirmNewPassword" name="confirmNewPassword" type="password"
                               class="form-control input-lg"
                               placeholder="Confirmar contrase&ntilde;a" required="true">
                    </div>
                    <div class="col-sm-3 no-padding sm-m-t-10 sm-text-center">
                        <button id="change-password" type="submit" class="btn btn-primary  btn-lg btn-large fs-15"
                                onclick="if (validateChangePasswordModal()) {
                                        changePassword('${pageContext.request.contextPath}/changePassword');}">Aceptar
                        </button>
                    </div>
                </div>
                <p class="text-center sm-text-center hinted-text p-t-10 p-r-10">Eliga una contrase&ntilde;a con al menos
                    una letra may&uacute;scula y como m&iacute;nimo 6 caracteres</p>
                <div id="changePasswordMessage"></div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- END MODAL CHANGE PASSWORD -->

<!-- MODAL PROFILE -->
<div class="modal fade fill-in" id="profileModal" tabindex="-1" role="dialog"
     aria-labelledby="profileModal" aria-hidden="true">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
            onclick="cleanProfileModal()"><i
            class="pg-close"></i>
    </button>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="windows">
                    <div id="profileMessage"></div>
                    <h5 class="text-left p-b-5"><span class="semi-bold">Datos del</span> perfil
                        <div class="pull-right hidden" id="progress-bar-profile">
                            <div class="col-sm-1 text-center center-scale">
                                <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                </div>
                            </div>
                        </div>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form role="form" method="POST">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group form-group-default required">
                                <label>Nombre</label>
                                <input id="profileName" name="profileName" type="text" class="form-control"
                                       placeholder="Nombre de la persona" required="true">
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group form-group-default required">
                                <label>Apellidos</label>
                                <input id="profileLastName" name="profileLastName" type="text" class="form-control"
                                       placeholder="Apellidos de la persona" required="true">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                                <input id="profileUserName" type="hidden">
                            <div class="form-group form-group-default">
                                <label>Correo</label>
                                <input id="profileEmail" name="profileEmail" type="text" class="form-control"
                                       placeholder="example@gmail.com" required="true">
                            </div>
                            <div class="form-group form-group-default">
                                <label>Identificación</label>
                                <input id="profileIdentification" name="profileIdentification" type="text"
                                       class="form-control"
                                       placeholder="Cédula o Carnet de Identificación" required="true">
                            </div>
                            <div class="form-group form-group-default">
                                <label id="profileOcupationLabel">Cargo</label>
                                <select id="profileOcupation" name="profileOcupation"
                                        tabindex="-1" title=""
                                        style="width: 100%;  border: 1px solid rgba(0, 0, 0, 0.07);"
                                        onblur="$('#profileOcupationLabel').removeAttr('class');">
                                    <option value="-1" selected="selectsed">Ninguno</option>
                                    <c:forEach var="ocupation" items="${profileOcupations}">
                                        <option value="${ocupation.id}">${ocupation.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <div class="fileinput fileinput-new" data-provides="fileinput"
                                     id="profilePhotoDiv">
                                    <div class="fileinput-new thumbnail"
                                         style="width: 270px; height: 160px;">
                                        <img src=""
                                             alt="">
                                    </div>
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 270px; max-height: 160px;" id="profilePhoto">
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
                    </div>
                    <button id="buttonSaveProfile" type="submit" class="btn btn-primary  btn-cons pull-left"
                            onclick="if (validateProfileModal()) {
                                    saveProfile('${pageContext.request.contextPath}');}return false;">Aceptar
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
<!-- END MODAL PROFILE -->
<security:authorize
        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_EXECUTER','ROLE_GENERAL_SUPERVISORY')">
    <!-- ENTITY DATA -->
    <div class="modal fade fill-in" id="entityDataModal" tabindex="-1" role="dialog"
         aria-labelledby="profileModal" aria-hidden="true">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                class="pg-close"></i>
        </button>
        <div class="modal-dialog full-width">
            <div class="modal-content full-width">
                <div class="modal-header">
                    <div class="windows">
                        <div id="entityMessage"></div>
                        <h4 class="text-left p-b-5"><span class="semi-bold">Datos generales de la entidad</span>

                        </h4>
                        <h5 class="text-left p-b-5"><span class="semi-bold">Imprimir: </span>
                            <a href="#" id="exportEntityData"
                               onclick="exportEntityData('${pageContext.request.contextPath}');"><img alt=""
                                                                                                      class="icon-pdf"
                                                                                                      data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                                                      data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                                                      src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                                                      width="25"
                                                                                                      height="25">
                            </a>
                            <div class="pull-right hidden" id="progress-bar-export-entity-data">
                                <div class="col-sm-1 text-center center-scale">
                                    <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                                    </div>
                                </div>
                            </div>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <form role="form" method="POST">
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="form-group form-group-default">
                                    <label>Nombre de la Entidad</label>
                                    <p>${entityData.name}</p>
                                </div>
                                <div class="form-group form-group-default">
                                    <label>Dirección</label>
                                    <p>${entityData.address}</p>
                                </div>
                                <div class="form-group form-group-default">
                                    <label>Dirección WEB</label>
                                    <p>${entityData.webAddress}<p>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <c:if test="${entityData.pathLogo!=null}">
                                        <img src="${entityData.pathLogo}">
                                        </img>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="form-group form-group-default">
                                    <label>Visión</label>
                                    <p>${entityData.vision}</p>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group form-group-default">
                                    <label>Misión</label>
                                    <p>${entityData.mission}</p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group form-group-default">
                                    <label>Objetivos Estratégicos</label>
                                    <c:forEach var="strategic_objective" items="${strategic_objectives}">
                                        <p>${strategic_objective.title} : ${strategic_objective.objective}</p>
                                    </c:forEach>

                                </div>
                            </div>
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
</security:authorize>
<!-- END ENTITY DATA -->
<!-- END HEADER -->
