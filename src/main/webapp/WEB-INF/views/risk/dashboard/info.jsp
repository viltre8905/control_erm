<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 20);%>
<% session.setAttribute("dashboard", "/risk/guide/guides");%>
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
                <div class="row m-t-10">
                    <div class="col-sm-5">
                        <!-- START WIDGET -->
                        <div id="portlet-widget-risk-level"
                             class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-black hint-text">
                          <span class="font-montserrat fs-11 all-caps">Análisis de Riesgos
                                        </span>
                                </div>
                                <div class="panel-controls">
                                    <ul>
                                        <li><a data-toggle="refresh" class="portlet-refresh text-black"
                                               href="#"><i
                                                class="portlet-icon portlet-icon-refresh"></i></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="panel-body p-t-40">
                                <div id="risk-level-container"></div>
                            </div>
                        </div>
                        <!-- END WIDGET -->
                    </div>
                    <div class="col-sm-7">
                        <!-- START WIDGET -->
                        <div id="portlet-widget-risk-top10"
                             class="widget-3 panel no-border bg-success no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-white">
                          <span class="font-montserrat fs-11 all-caps">Objetivos más afectados
                                        </span>
                                </div>
                                <div class="panel-controls">
                                    <ul>
                                        <li><a data-toggle="refresh" class="portlet-refresh text-white"
                                               href="#"><i
                                                class="portlet-icon portlet-icon-refresh"></i></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="panel-body p-t-40">
                                <div class="panel panel-default m-t-10">
                                    <div class="panel-body">
                                        <div id="tableWithSearch_wrapper"
                                             class="dataTables_wrapper form-inline no-footer">
                                            <div class="table-responsive">
                                                <table id="tableObjectivesMoreAffected"
                                                       class="table table-hover demo-table-search dataTable no-footer"
                                                       role="grid" aria-describedby="tableWithSearch_info">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="sorting_asc" tabindex="0"
                                                            aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1" style="width: 40px;" aria-sort="ascending">
                                                            No
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1" style="width: 400px;">
                                                            Objetivo
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1" style="width: 250px;">Proceso o Sub-Proceso
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <% int i = 1;%>
                                                    <c:forEach var="objective"
                                                               items="${objectivesAffected}">
                                                        <tr role="row" class="odd">
                                                            <td class="v-align-middle semi-bold sorting_1">
                                                                <p><%= i %><%i++;%>
                                                                </p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${objective.objective}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${objective.process.name}</p>
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

                            </div>
                        </div>
                        <!-- END WIDGET -->
                    </div>
                </div>
            </div>
        </div>
        <!-- BEGIN COPYRIGHT -->
        <%@include file="/WEB-INF/views/template/copyright.jsp" %>
        <!-- END COPYRIGHT -->
    </div>
    <!-- BEGIN JS -->
    <%@include file="/WEB-INF/views/template/script.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/plugins/Highchart-4.0.4/js/highcharts.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/dashboard_risk.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/template/dashboard.js"
            type="text/javascript"></script>
    <script>
        $('#summarySubProcessButton').click(function (event) {
            event.preventDefault();
            summarySubProcess(this);
        });
        $('#portlet-widget-3').portlet({
            progress: 'circle',
            onRefresh: function () {
                drawSparklinePie();
            }
        });
        $('#portlet-widget-3').portlet({
            refresh: true
        });
        riskLevelDashboard(true);
        var table = $('#tableObjectivesMoreAffected');
        var settings = {
            "sDom": "<'table-responsive't><'row'<p i>>",
            "sPaginationType": "bootstrap",
            "destroy": true,
            "scrollCollapse": true,
            "oLanguage": {
                "sLengthMenu": "_MENU_ ",
                "sInfo": "Mostrando <b>_START_ al _END_</b> de _TOTAL_ total"
            },
            "iDisplayLength": 5
        };
        table.dataTable(settings);
        initDashboard();
    </script>
    <!-- END JS -->
</body>
</html>
