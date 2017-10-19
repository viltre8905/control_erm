<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bienvenido a Control ERM</title>
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
</head>

<body id="withoutPin" class="fixed-header">
<!-- START PAGE-CONTAINER -->
<div class="page-container">
    <!-- START HEADER -->
    <%@include file="/WEB-INF/views/template/navBarSupervisory.jsp" %>
    <!-- END HEADER -->
    <div class="page-content-wrapper">
        <div class="content">
            <div class="container-fluid container-fixed-lg">
                <c:if test="${entityData.parent!=null
                && !entityData.id.equals(userLogged.entity.id)}">
                    <button class="btn btn-tag btn-tag-light pull-right m-b-5" type="button"
                            onclick="undoEntity()">
                        Volver
                    </button>
                </c:if>
                <div class="row m-b-10">
                    <div class="col-sm-12">
                        <!-- START WIDGET -->
                        <div id="dashboard-porlet${entityData.id}"
                             class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                            <div class="panel-heading top-left top-right ">
                                <div class="panel-title text-black hint-text">
                                            <span class="font-montserrat fs-11 all-caps">${entityData.name}
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
                                    <div class="col-sm-6">
                                        <div id="speedometer${entityData.id}"
                                             style="min-width: 100px; max-width: 210px; height: 210px; margin: 0 auto"></div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <div id="dashboard-sparkline-pie${entityData.id}"
                                                     class="sparkline-chart m-t-10"></div>
                                            </div>
                                            <div class="col-sm-6">
                                                <div class="m-t-20">
                                                    <p class="hint-text all-caps font-montserrat small no-margin ">
                                                        Resueltas</p>
                                                    <p id="dashboard-sparkline-solved${entityData.id}"
                                                       class="all-caps font-montserrat  no-margin text-complete "></p>
                                                </div>
                                                <div>
                                                    <p class="hint-text all-caps font-montserrat small no-margin ">
                                                        Cierran
                                                        Hoy</p>
                                                    <p id="dashboard-sparkline-closed${entityData.id}"
                                                       class="all-caps font-montserrat  no-margin text-warning "></p>
                                                </div>
                                                <div>
                                                    <p class="hint-text all-caps font-montserrat small no-margin ">
                                                        Incumplidas</p>
                                                    <p id="dashboard-sparkline-nocomp${entityData.id}"
                                                       class="all-caps font-montserrat  no-margin text-danger "></p>
                                                </div>
                                                <div>
                                                    <p class="hint-text all-caps font-montserrat small no-margin ">En
                                                        tiempo</p>
                                                    <p id="dashboard-sparkline-intime${entityData.id}"
                                                       class="all-caps font-montserrat  no-margin text-success "></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <input class="input-entity-dashboard-main" type="text" hidden="true"
                                           value="${entityData.id}"/>
                                </div>
                            </div>
                        </div>
                        <div class="p-t-10 full-width">
                            <div class="row">
                                <div class="col-sm-11 m-t-10">
                                </div>
                                <div class="col-sm-1">
                                    <button class="btn btn-primary m-b-5" onclick="enterEntity('${entityData.id}')">
                                        Entrar
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <% int i = 0;%>
                <c:forEach var="entityDataObject" items="${entityDataList}">
                    <% if (i % 3 == 0) {%>
                    <div class="row m-b-10">
                        <% }
                            i++;%>
                        <div class="col-sm-4">
                            <!-- START WIDGET -->
                            <div id="dashboard-porlet${entityDataObject.id}"
                                 class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                                <div class="panel-heading top-left top-right ">
                                    <div class="panel-title text-black hint-text">
                                        <span class="font-montserrat fs-11 all-caps">${entityDataObject.name}</span>
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
                                        <div class="col-sm-6">
                                            <div id="speedometer${entityDataObject.id}"
                                                 style="min-width: 100px; max-width: 210px; height: 210px; margin: 0 auto"></div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div id="dashboard-sparkline-pie${entityDataObject.id}"
                                                         class="sparkline-chart m-t-10"></div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <div class="m-t-20">
                                                        <p class="hint-text all-caps font-montserrat small no-margin"
                                                           style="font-size: xx-small;">
                                                            Resueltas</p>
                                                        <p id="dashboard-sparkline-solved${entityDataObject.id}"
                                                           class="all-caps font-montserrat  no-margin text-success "
                                                           style="font-size: xx-small;">
                                                        </p>
                                                    </div>
                                                    <div>
                                                        <p class="hint-text all-caps font-montserrat small no-margin "
                                                           style="font-size: xx-small;">
                                                            Cierran
                                                            Hoy</p>
                                                        <p id="dashboard-sparkline-closed${entityDataObject.id}"
                                                           class="all-caps font-montserrat  no-margin text-warning "
                                                           style="font-size: xx-small;">
                                                        </p>
                                                    </div>
                                                    <div>
                                                        <p class="hint-text all-caps font-montserrat small no-margin "
                                                           style="font-size: xx-small;">
                                                            Incumplidas</p>
                                                        <p id="dashboard-sparkline-nocomp${entityDataObject.id}"
                                                           class="all-caps font-montserrat  no-margin text-danger "
                                                           style="font-size: xx-small;">
                                                        </p>
                                                    </div>
                                                    <div>
                                                        <p class="hint-text all-caps font-montserrat small no-margin "
                                                           style="font-size: xx-small;">
                                                            En
                                                            tiempo</p>
                                                        <p id="dashboard-sparkline-intime${entityDataObject.id}"
                                                           class="all-caps font-montserrat  no-margin text-complete "
                                                           style="font-size: xx-small;">
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <input class="input-entity-dashboard" type="text" hidden="true"
                                               value="${entityDataObject.id}"/>
                                    </div>
                                </div>

                                <div class="p-t-10 full-width">
                                    <div class="row">
                                        <div class="col-sm-9 m-t-10">
                                            <c:choose>
                                                <c:when test="${entityDataObject.entities.size()>0}">
                                                    <span class="text-info m-l-10">Entidades hijas</span>
                                                    <button class="fa fa-search"
                                                            onclick="changeEntity('${entityDataObject.id}')"></button>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="m-l-10">Sin entidades hijas</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>

                                </div>

                            </div>
                            <div class="p-t-10 full-width">
                                <div class="row">
                                    <div class="col-sm-9 m-t-10">
                                    </div>
                                    <div class="col-sm-3">
                                        <button class="btn btn-primary m-b-5"
                                                onclick="enterEntity('${entityDataObject.id}')">Entrar
                                        </button>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <% if (i % 3 == 0) {%>
                    </div>

                    <% }%>
                </c:forEach>
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
    <script src="${pageContext.request.contextPath}/resources/js/supervisory_home.js"
            type="text/javascript"></script>
    <script>
        supervisoryHome();
    </script>
</div>
</body>
</html>
