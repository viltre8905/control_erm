<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Licencia</title>
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
</head>

<body class="fixed-header error-page  ">
<div class="container-xs-height full-height">
    <div class="row-xs-height">
        <div class="col-xs-height col-middle">
            <div class="error-container text-center">
                <h2 class="error-number">Sin Licencia</h2>
                <h3 class="semi-bold">Lo sentimos pero no puede acceder al sistema</h3>
                <p>Necesita introducir la clave de la licencia para poder acceder al sistema para poder acceder al
                    sistema.
                </p>
                <div class="error-container-innner text-left">
                    <form>
                        <div class="form-group form-group-default">
                            <label>Introducir Licencia</label>
                            <div class="fileinput fileinput-new" data-provides="fileinput"
                                 style="width: 100%;">
                                <div id="fileName" class="fileinput-filename p-l-10 p-t-15"
                                     style="border: 1px solid rgba(0, 0, 0, 0.07); height: 40px;"></div>
                                <div class="m-t-5">
                                            <span class="btn default btn-file">
                                            <span class="fileinput-new">Subir Archivo</span>
											<span class="fileinput-exists">Cambiar </span>
											<input id="file" name="..." type="file" class=""></span>
                                    <a href="#" class="btn default fileinput-exists"
                                       data-dismiss="fileinput">
                                        Eliminar </a>
                                </div>
                            </div>
                        </div>
                        <button id="add-activity-button" class="btn btn-primary  btn-cons pull-right"
                                onclick="if(validateLicence()){
                                addLicence();
                                } return false;">Continuar
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="pull-bottom sm-pull-bottom full-width">
    <div class="error-container">
        <div class="error-container-innner">
            <div class="m-b-30 sm-m-t-20 sm-p-r-15 sm-p-b-20 clearfix">
                <div class="col-sm-3 no-padding">
                    <img alt="" class="m-t-5" data-src="${pageContext.request.contextPath}/resources/img/logo_icon.png"
                         data-src-retina="${pageContext.request.contextPath}/resources/img/logo_icon.png" height="60"
                         src="${pageContext.request.contextPath}/resources/img/logo_icon.png" width="60">
                </div>
                <div class="col-sm-9 no-padding">
                    <p>
                        <span class="hint-text">Copyright &copy; 2016 </span>
                        <span class="font-montserrat">CONTROL ERM</span>.
                        <span class="hint-text">Todos los derechos reservados. </span>
                        <span class="hint-text">Desarrollado por</span>
                        <a href="#">Dainiel Viltre Guillen & Ramón Treto Ocumárez</a>
                        <span class="hint-text">&reg;</span>
                    </p>

                </div>
            </div>
        </div>
    </div>
</div>

<!-- BEGIN JS -->
<%@include file="/WEB-INF/views/template/script.jsp" %>

</body>
</html>
