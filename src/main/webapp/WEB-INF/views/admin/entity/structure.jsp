<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 4);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Estructura de las Entidades</title>
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
    <link href="${pageContext.request.contextPath}/resources/plugins/jquery-nestable/jquery.nestable.min.css"
          rel="stylesheet" type="text/css"/>
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
                        <div class="panel-title">Estructura de las Entidades
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="panel-body">
                        <div class="dd" id="entity-structure">
                            <ol class="dd-list" id="entity-global-container">
                                <c:forEach var="entity" items="${entitiesWithOutParent}">
                                    ${entity.toHtmlNestable()}
                                </c:forEach>
                            </ol>
                        </div>
                    </div>
                </div>
                <!-- END PANEL -->
            </div>


        </div>

        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/plugins/jquery-nestable/jquery.nestable.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/admin/general_admin.js"
            type="text/javascript"></script>
    <script>
        (function ($) {
            'use strict';
            $('#entity-structure').nestable();
        })(window.jQuery);
    </script>
</div>
</body>
</html>

