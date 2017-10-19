<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 46);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Plan Medidas de la Entidad </title>
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
                <div class="windows">
                    <h3>
                        Plan de Medidas de la Empresa: ${entityData.name}
                    </h3>
                </div>
                <p>A continuaci&oacute;n se detallan el plan de medidas por los proceso de la empresa.
                </p>
                <p>Imprimir Plan de Medidas de la Empresa: <a href="#" id="exportReport"><img alt="" class="icon-pdf"
                                                                                              data-src-retina="${pageContext.request.contextPath}/resources/img/report/pdf2x.png"
                                                                                              data-src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                                              src="${pageContext.request.contextPath}/resources/img/report/pdf.png"
                                                                                              width="25" height="25">
                </a></p>
                <div class="progress-bar-container hidden" id="progress-bar-export-report">
                    <div class="col-sm-1 text-center center-scale">
                        <div class="progress-circle-indeterminate m-t-0" data-color="primary">
                        </div>
                    </div>
                </div>
                <br/>
                <% int i = 1;%>
                <!-- MEASURES PANELS -->
                <c:forEach var="processName" items="${measuresPlan.keySet()}">
                    <div class="panel panel-group panel-transparent" data-toggle="collapse" id="accordion<%= i %>">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a class="collapsed" data-parent="#accordion<%= i++ %>" data-toggle=
                                            "collapse" href="#collapse<%= i %>">${processName}</a>
                                </h4>
                            </div>
                            <div class="panel-collapse collapse" id="collapse<%= i++ %>">
                                <div class="panel-body">

                                    <c:forEach var="componentsName"
                                               items="${measuresPlan.get(processName).keySet()}">
                                        <div class="panel panel-group panel-transparent" data-toggle="collapse"
                                             id="accordion<%= i %>">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a class="collapsed" data-parent="#accordion<%= i++ %>"
                                                           data-toggle=
                                                                   "collapse"
                                                           href="#collapse<%= i %>">${componentsName}</a>
                                                    </h4>
                                                </div>
                                                <div class="panel-collapse collapse" id="collapse<%= i++ %>">
                                                    <div class="panel-body">

                                                        <c:forEach var="deficiency"
                                                                   items="${measuresPlan.get(processName).get(componentsName).keySet()}">
                                                            <div class="panel panel-group panel-transparent"
                                                                 data-toggle="collapse" id="accordion<%= i %>">

                                                                <div class="panel panel-default">
                                                                    <div class="panel-heading">
                                                                        <h4 class="panel-title">
                                                                            <a class="collapsed"
                                                                               data-parent="#accordion<%= i++ %>"
                                                                               data-toggle=
                                                                                       "collapse"
                                                                               href="#collapse<%= i %>">${deficiency}</a>
                                                                        </h4>
                                                                    </div>
                                                                    <div class="panel-collapse collapse"
                                                                         id="collapse<%= i++ %>">
                                                                        <div class="panel-body">
                                                                            <div id="tableWithSearch_wrapper"
                                                                                 class="dataTables_wrapper form-inline no-footer">
                                                                                <div class="table-responsive">
                                                                                    <table id="tableWithSearch${deficiency}"
                                                                                           name="tableWithSearch"
                                                                                           class="table table-hover demo-table-search dataTable no-footer"
                                                                                           role="grid"
                                                                                           aria-describedby="tableWithSearch_info">
                                                                                        <thead>
                                                                                        <tr role="row">
                                                                                            <th class="sorting_asc"
                                                                                                tabindex="0"
                                                                                                aria-controls="tableWithSearch"
                                                                                                rowspan="1"
                                                                                                colspan="1"
                                                                                                style="width: 200px;"
                                                                                                aria-sort="ascending">
                                                                                                Descripción
                                                                                            </th>
                                                                                            <th class="sorting"
                                                                                                tabindex="0"
                                                                                                aria-controls="tableWithSearch"
                                                                                                rowspan="1"
                                                                                                colspan="1"
                                                                                                style="width: 120px;">
                                                                                                Responsable
                                                                                            </th>
                                                                                            <th class="sorting"
                                                                                                tabindex="0"
                                                                                                aria-controls="tableWithSearch"
                                                                                                rowspan="1"
                                                                                                colspan="1"
                                                                                                style="width: 120px;">
                                                                                                Ejecutor
                                                                                            </th>
                                                                                            <th class="sorting"
                                                                                                tabindex="0"
                                                                                                aria-controls="tableWithSearch"
                                                                                                rowspan="1"
                                                                                                colspan="1"
                                                                                                style="width: 80px;">
                                                                                                Estado
                                                                                            </th>
                                                                                            <th class="sorting"
                                                                                                tabindex="0"
                                                                                                aria-controls="tableWithSearch"
                                                                                                rowspan="1"
                                                                                                colspan="1"
                                                                                                style="width: 80px;">
                                                                                                Fecha
                                                                                            </th>
                                                                                        </tr>
                                                                                        </thead>
                                                                                        <tbody>
                                                                                        <c:forEach var="activity"
                                                                                                   items="${measuresPlan.get(processName).get(componentsName).get(deficiency)}">
                                                                                            <tr role="row" class="odd">
                                                                                                <td class="v-align-middle semi-bold sorting_1">
                                                                                                    <p>${activity.activityDescription}</p>
                                                                                                </td>
                                                                                                <td class="v-align-middle">
                                                                                                    <p>${activity.responsible.firstName} ${activity.responsible.lastName}</p>
                                                                                                </td>
                                                                                                <td class="v-align-middle">
                                                                                                    <p>${activity.executor.firstName} ${activity.executor.lastName}</p>
                                                                                                </td>
                                                                                                <td class="v-align-middle">
                                                                                                    <p>${activity.activityState.name}</p>
                                                                                                </td>
                                                                                                <td class="v-align-middle">
                                                                                                    <p>${activity.toShortAccomplishDate()}</p>
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
                                                        </c:forEach>

                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </c:forEach>

                                </div>
                            </div>
                        </div>

                    </div>
                </c:forEach>
                <!-- END MEASURES PANELS -->

            </div>
            <!-- BEGIN COPYRIGHT -->
            <%@include file="/WEB-INF/views/template/copyright.jsp" %>
            <!-- END COPYRIGHT -->
        </div>
        <!-- BEGIN JS -->
        <%@include file="/WEB-INF/views/template/script.jsp" %>
        <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/preventionplan/prevention_plan.js"
                type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/general_committee.js"
                type="text/javascript"></script>
        <script>

            var tables = document.getElementsByName("tableWithSearch");
            for (var i = 0; i < tables.length; i++) {
                var id = tables[i].getAttribute("id");
                generalInitTable(id);
            }
        </script>
        <!-- END JS -->
    </div>
    <script>

        var tables = document.getElementsByName("tableWithSearch");
        for (var i = 0; i < tables.length; i++) {
            var id = tables[i].getAttribute("id");
            generalInitTable(id);
        }
    </script>
    <!-- END ID -->
</div>
</body>
</html>



