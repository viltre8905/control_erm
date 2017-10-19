<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 45);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Plan General de Prevención de Riesgos </title>
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
                        Riesgos de la Empresa: ${entityData.name}
                    </h3>
                </div>
                <p>A continuaci&oacute;n se detallan los riesgos agrupados por los proceso de la empresa.
                </p>
                <p>Imprimir Plan de prevención de riesgos: <a href="#" id="exportReport"><img alt="" class="icon-pdf"
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
                <c:forEach var="process" items="${all_processes}">
                    <div class="windows">
                        <h5>
                                ${process.name}
                        </h5>
                    </div>
                    <!-- RISK PANELS -->
                    <div class="panel panel-group panel-transparent" data-toggle="collapse" id="accordion">
                        <c:forEach var="objective" items="${process.objectives}">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a class="collapsed" data-parent="#accordion" data-toggle=
                                                "collapse" href="#collapse${objective.id}">${objective.objective}</a>
                                    </h4>
                                </div>
                                <div class="panel-collapse collapse" id="collapse${objective.id}">
                                    <div class="panel-body">
                                        <div id="tableWithSearch_wrapper"
                                             class="dataTables_wrapper form-inline no-footer">
                                            <div class="table-responsive">
                                                <table id="tableWithSearch${objective.id}"
                                                       name="tableWithSearch"
                                                       class="table table-hover demo-table-search dataTable no-footer"
                                                       role="grid" aria-describedby="tableWithSearch_info">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="sorting_asc" tabindex="0"
                                                            aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1" style="width: 180px;" aria-sort="ascending">
                                                            Descripción
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1" style="width: 80px;">Probabilidad
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1"
                                                            style="width: 80px;">Impacto
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1"
                                                            style="width: 80px;">Procedencia
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="tableWithSearch"
                                                            rowspan="1"
                                                            colspan="1"
                                                            style="width: 80px;">Nivel
                                                        </th>
                                                        <security:authorize
                                                                access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                            <th tabindex="0" aria-controls="tableWithSearch" rowspan="1"
                                                                colspan="1"
                                                                style="width: 80px;">Incluir en Reporte
                                                            </th>
                                                        </security:authorize>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="risk" items="${objective.risks}">
                                                        <tr role="row" class="odd">

                                                            <td class="v-align-middle semi-bold sorting_1">
                                                                <p>${risk.description}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${risk.probability}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${risk.impact}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${risk.Procedence()}</p>
                                                            </td>
                                                            <td class="v-align-middle">
                                                                <p>${risk.level}</p>
                                                            </td>
                                                            <security:authorize
                                                                    access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                                <td class="v-align-middle">
                                                                    <div class="row">
                                                                        <div class="btn-group b-grey b-l b-r p-l-15 p-r-15">
                                                                            <c:choose>
                                                                                <c:when test="${risk.includeInReport}">
                                                                                    <input id="switchery${risk.id}"
                                                                                           type="checkbox"
                                                                                           data-init-plugin="switchery"
                                                                                           checked="checked"
                                                                                           onchange="riskStatus(this)"/>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <input id="switchery${risk.id}"
                                                                                           type="checkbox"
                                                                                           data-init-plugin="switchery"
                                                                                           onchange="riskStatus(this)"/>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </div>
                                                                    </div>
                                                                    <input type="text" hidden="true"
                                                                           value="${risk.id}"/>
                                                                </td>
                                                            </security:authorize>
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
                        </c:forEach>
                    </div>
                    <!-- END RISK PANELS -->
                </c:forEach>

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
