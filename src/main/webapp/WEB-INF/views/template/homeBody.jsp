<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bienvenido a Control ERM</title>
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
            <div class="container-fluid">
                <div class="row">
                    <h1 class="m-b-10">
                        Implementación de los Cuestionarios (Autocontrol)
                    </h1>
                </div>
                <div class="row m-b-10">
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <!-- START WIDGET -->
                        <div id="dashboard-porlet1" class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-black hint-text">
                                        <span class="font-montserrat fs-11 all-caps">Resultado general de la empresa
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
                            <div class="panel-body p-t-40" style=" height: 270px;">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <h3 id="efficacy-header" class="no-margin p-b-5 text-white semi-bold">Eficacia</h3>
                                        <p class="text-black m-t-15">Resumen:</p>
                                        <div class="pull-left">
                                            <span class="text-black">Total de preguntas </span>
                                                        <span id="questionCount" class=" text-white font-montserrat">
                                                        </span>
                                        </div>

                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="pull-left">
                                            <span class="text-black">Afirmativas </span>
                                        <span id="countA" class=" text-white font-montserrat">
                                        </span>
                                        </div>
                                        <div class="pull-left m-l-20 small">
                                            <span class="text-black">Negativas </span>
                                            <span id="countN" class=" text-white font-montserrat">
                                            </span>
                                        </div>

                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="pull-left small">
                                            <span class="text-black">No proceden </span>
                                            <span id="countNP" class=" text-white font-montserrat">
                                            </span>
                                        </div>
                                        <div class="pull-left m-l-20 small">
                                            <span class="text-black">Sin responder </span>
                                            <span id="nAnswer" class=" text-white font-montserrat">
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-porlet2"
                             class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-black hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Entorno de Control
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
                                <div class="row">
                                    <div id="speedometer2"
                                         style="min-width: 100px; max-width: 210px; height: 210px; margin: 0 auto"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-porlet3"
                             class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right">
                                <div class="panel-title text-black hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Evaluación de Riesgo
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
                                <div class="row">
                                    <div id="speedometer3"
                                         style="min-width: 100px; max-width: 210px; height: 210px; margin: 0 auto"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-porlet4"
                             class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-black hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Actividades de Control
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
                                <div class="row">
                                    <div id="speedometer4"
                                         style="min-width: 100px; max-width: 250px; height: 210px; margin: 0 auto"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-porlet5"
                             class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-black hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Información y Comunicación
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
                                <div class="row">
                                    <div id="speedometer5"
                                         style="min-width: 100px; max-width: 250px; height: 210px; margin: 0 auto"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-porlet6"
                             class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-black hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Supervisión y Monitoreo
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
                                <div class="row">
                                    <div id="speedometer6"
                                         style="min-width: 100px; max-width: 250px; height: 210px; margin: 0 auto"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <h1 class="m-b-10">
                        Estado del Plan de Medidas
                    </h1>
                </div>
                <div class="row m-b-10">

                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-sparkline-pie1"
                             class="widget-3 panel no-border bg-master-dark no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-white hint-text">
                                    <c:choose>
                                        <c:when test="${processSelected==-1}">
                                            <span class="font-montserrat fs-11 all-caps">Resultado general de la Empresa
                                        </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="font-montserrat fs-11 all-caps">Resultado general del proceso
                                        </span>
                                        </c:otherwise>
                                    </c:choose>
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
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div id="sparkline-pie1" class="sparkline-chart m-t-10"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-sparkline-pie2"
                             class="widget-3 panel no-border bg-master-dark no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-white hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Entorno de Control
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
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div id="sparkline-pie2" class="sparkline-chart m-t-10"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- START WIDGET -->
                    </div>
                    <div class="col-sm-4">
                            <div id="dashboard-sparkline-pie3"
                                 class="widget-3 panel no-border bg-master-dark no-margin widget-loader-bar">
                                <div class="panel-heading top-left top-right ">
                                    <div class="panel-title text-white hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Evaluación de Riesgo
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
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div id="sparkline-pie3" class="sparkline-chart m-t-10"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-sparkline-pie4"
                             class="widget-3 panel no-border bg-master-dark no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-white hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Actividades de Control
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
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div id="sparkline-pie4" class="sparkline-chart m-t-10"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-sparkline-pie5"
                             class="widget-3 panel no-border bg-master-dark no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-white hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Información y Comunicación
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
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div id="sparkline-pie5" class="sparkline-chart m-t-10"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- START WIDGET -->
                        <div id="dashboard-sparkline-pie6"
                             class="widget-3 panel no-border bg-master-dark no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-white hint-text">
                                 <span class="font-montserrat fs-11 all-caps">Supervisión y Monitoreo
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
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div id="sparkline-pie6" class="sparkline-chart m-t-10"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <h1 class="m-b-10">
                        Análisis de Riesgos
                    </h1>
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
    <script src="${pageContext.request.contextPath}/resources/plugins/Highchart-4.0.4/js/highcharts-more.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/general_dashboard.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/dashboard_risk.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        dashboard('${pageContext.request.contextPath}');
        riskLevelDashboard(false);
    </script>
    <!-- END JS -->

</body>
</html>
