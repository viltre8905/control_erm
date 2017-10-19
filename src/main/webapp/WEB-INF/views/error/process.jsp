<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
</head>

<body class="fixed-header error-page  ">
<div class="container-xs-height full-height">
    <div class="row-xs-height">
        <div class="col-xs-height col-middle">
            <div class="error-container text-center">
                <h1 class="error-number">Error</h1>
                <h2 class="semi-bold">Lo sentimos pero no puede acceder al sistema</h2>
                <p>Necesita estar involucrado en un proceso para poder acceder al sistema.
                </p>

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
                        <small>Contacte con el admistrador del sistema para que lo asigne a un proceso y vuelva a intentar conectarse.
                            <a href="${pageContext.request.contextPath}/home">Volver a conectarse</a> o <a href="${pageContext.request.contextPath}/logout">Salir</a></small>
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
