<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Control ERM Inicio</title>
    <!-- START PAGE-STYLES -->
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
    <!-- END PAGE-STYLES -->
</head>

<body class="fixed-header   ">
<!-- START PAGE-CONTAINER -->
<div class="login-wrapper ">
    <!-- START Login Background Pic Wrapper-->
    <div class="bg-pic">
        <!-- START Background Pic-->
        <img src="resources/img/imgseguridad.jpg"
             data-src="resources/img/imgseguridad.jpg"
             data-src-retina="resources/img/imgseguridad.jpg" alt="" class="lazy">
        <!-- END Background Pic-->

        <!-- START Background Caption-->
        <div class="bg-caption pull-bottom sm-pull-bottom text-white p-l-20 m-b-20">
            <h2 class="semi-bold text-white">
                <strong>Control ERM</strong> Sistema de Control Interno.</h2>
        </div>
        <!-- END Background Caption-->
    </div>
    <!-- END Login Background Pic Wrapper-->

    <!-- START Login Right Container-->
    <div class="login-container bg-white">
        <div class="p-l-50 m-l-20 p-r-50 m-r-20 p-t-50 m-t-30 sm-p-l-15 sm-p-r-15 sm-p-t-40">
            <img src="resources/img/logo.png" alt="logo"
                 data-src="resources/img/logo.png"
                 data-src-retina="resources/img/logo.png" width="140" height="30">
            <p class="p-t-35">Bienvenido a Control ERM</p>
            <!-- START Login Form -->
            <security:authorize ifNotGranted="ROLE_user">
                <form id="login" name="login" class="p-t-15" role="form" action="j_spring_security_check" method="POST">
                    <!-- START Form Control-->
                    <div class="form-group form-group-default">
                        <label>Usuario</label>
                        <div class="controls">
                            <input id="j_username" type="text" name="j_username" placeholder="Usuario"
                                   class="form-control" required>
                        </div>
                    </div>
                    <!-- END Form Control-->
                    <!-- START Form Control-->
                    <div class="form-group form-group-default">
                        <label>Contraseña</label>
                        <div class="controls">
                            <input id="j_password" type="password" class="form-control" name="j_password"
                                   placeholder="Credenciales" required>
                        </div>
                    </div>
                    <button class="btn btn-complete btn-cons m-t-10" type="submit">Autenticar</button>
                </form>
                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
                    <div data-position="top-right" class="pgn-wrapper">
                        <div class="pgn pgn-flip">
                            <div class="alert alert-danger">
                                <button data-dismiss="alert" class="close" type="button">
                                    <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                                </button>
                                <span>${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
                            </div>
                        </div>
                    </div>
                </c:if>
            </security:authorize>
            <!--END Login Form-->
            <div class="pull-bottom sm-pull-bottom">
                <div class="m-b-30 p-r-80 sm-m-t-20 sm-p-r-15 sm-p-b-20 clearfix">
                    <div class="col-sm-3 col-md-2 no-padding">
                        <img alt="" class="m-t-5" data-src="${pageContext.request.contextPath}/resources/img/logo_icon.png" data-src-retina="${pageContext.request.contextPath}/resources/img/logo_icon.png" height="60" src="${pageContext.request.contextPath}/resources/img/logo_icon.png" width="60">
                    </div>
                    <div class="col-sm-9 no-padding m-t-10">
                        <p><small>
                            Puede acceder al sistema con su cuenta. En caso de no contar con una contacte con el administrador del sistema.</small>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- END Login Right Container-->
</div>
<!-- END PAGE CONTAINER -->


<!-- BEGIN JS -->
<%@include file="/WEB-INF/views/template/script.jsp" %>
<security:authorize ifNotGranted="ROLE_user">
    <script>
        $(function () {
            $("#login").validate();
        })
    </script>
</security:authorize>
<!-- END JS -->
</body>
</html>