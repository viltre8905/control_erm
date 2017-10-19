<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 38);%>
<% session.setAttribute("dashboard", "/monitoring/guide/guides");%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Información General</title>
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
</head>
<body class="fixed-header">

<%@include file="/WEB-INF/views/template/sideBar.jsp" %>
<!-- START PAGE-CONTAINER -->
<div class="page-container">
    <!-- START HEADER -->
    <%@include file="/WEB-INF/views/template/navBarDashboard.jsp" %>
    <!-- END HEADER -->
    <div class="page-content-wrapper">
        <div class="content">
            <!-- START CONTAINER FLUID -->
            <div class="container-fluid padding-25 sm-padding-10">
                <div class="row">
                    <%@include file="/WEB-INF/views/template/dashboard.jsp" %>
                </div>
            </div>
        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/template/dashboard.js"
            type="text/javascript"></script>
    <script>
        $('#summarySubProcessButton').click(function (event) {
            event.preventDefault();
            summarySubProcess(this);
        });
        $('#portlet-widget-3').portlet({
            progress: 'circle',
            onRefresh: function() {
                drawSparklinePie();
            }
        });
        $('#portlet-widget-3').portlet({
            refresh: true
        });
        initDashboard();
    </script>
    <!-- END JS -->
</body>
</html>
