<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="container-xs-height full-height">
    <div class="row-xs-height">
        <div class="col-xs-height col-middle">
            <div class="error-container text-center">
                <h1 id="error-title" class="error-number">Error</h1>
                <h2 id="error-message" class="semi-bold">Ha ocurrido un error inesperado</h2>
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
                        <small>Contacte con el admistrador del sistema para obtener más información.</small>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>