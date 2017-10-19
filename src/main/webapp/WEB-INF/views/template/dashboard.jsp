<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="col-sm-5">
    <div class="row m-b-10">
        <div class="col-sm-12">
            <!-- START WIDGET -->
            <div id="guide-porlet" class="widget-3 panel no-border bg-white no-margin widget-loader-bar">
                <div class="panel-heading top-left top-right ">
                    <div class="panel-title text-black hint-text">
                                        <span class="font-montserrat fs-11 all-caps">Implementación de los cuestionarios
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
                    <div class="p-t-10 full-width">
                        <a href="${pageContext.request.contextPath}<%= session.getAttribute("dashboard")%>">
                            <span class="hint-text small">Ver más</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <!-- START WIDGET -->
            <div id="procedure-porlet" class="widget-2 panel no-border bg-white no-margin widget-loader-bar">
                <div class="panel-heading top-left top-right ">
                    <div class="panel-title text-black hint-text">
                        <span class="font-montserrat fs-11 all-caps">Estado de los procedimientos</span>
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
                            <h3 id="procedureResult" class="no-margin p-b-5 text-success semi-bold"></h3>
                            <div class="pull-left">
                                <span>Procedimientos Aprobados</span>
                                    <span id="procedurePercent" class="text-success font-montserrat">
                                    <i class="fa fa-caret-up m-l-10"></i>
                                    </span>
                            </div>
                        </div>
                    </div>
                    <div class="p-t-10 full-width">
                        <a href="documents">
                            <span class="hint-text small">Ver más</span>
                        </a>
                    </div>
                </div>
            </div>
            <!-- END WIDGET -->
        </div>
    </div>
</div>
<div class="col-sm-7">
    <!-- START WIDGET -->
    <div id="portlet-widget-3" class="widget-3 panel no-border bg-master-dark no-margin widget-loader-bar">
        <div class="panel-heading top-left top-right ">
            <div class="panel-title text-white hint-text">
                          <span class="font-montserrat fs-11 all-caps">Estado de las Medidas
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
        <div id='activitiesStateBody' class="panel-body p-t-40">
            <div class="row">
                <div class="col-sm-6">
                    <div id="sparkline-pie" class="sparkline-chart m-t-10"></div>
                </div>
                <div class="col-sm-6">
                    <div class="m-t-20">
                        <p class="hint-text all-caps font-montserrat small no-margin ">Resueltas</p>
                        <p class="all-caps font-montserrat  no-margin text-complete ">${activitiesState.get("solvedCount")}</p>
                    </div>
                    <div>
                        <p class="hint-text all-caps font-montserrat small no-margin ">Cierran
                            Hoy</p>
                        <p class="all-caps font-montserrat  no-margin text-warning ">${activitiesState.get("closeToday")}</p>
                    </div>
                    <div>
                        <p class="hint-text all-caps font-montserrat small no-margin ">
                            Incumplidas</p>
                        <p class="all-caps font-montserrat  no-margin text-danger ">${activitiesState.get("nonCompletionCount")}</p>
                    </div>
                    <div>
                        <p class="hint-text all-caps font-montserrat small no-margin ">En tiempo</p>
                        <p class="all-caps font-montserrat  no-margin text-success ">${activitiesState.get("inTime")}</p>
                    </div>
                </div>
            </div>
            <security:authorize
                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY')">
                <div id="summarySubProcess" class="hidden">
                    <div class="row m-t-20 m-b-5">
                        <div class="col-sm-12">
                            <div class="pull-left">
                                <span class="text-complete">Resumen por y sub-procesos y actividades:</span>
                            </div>
                        </div>
                    </div>
                    <c:forEach var="subProcessMap"
                               items='${activitiesState.get("subProcesses")}'>
                        <div class="row m-b-5">
                            <div class="col-sm-12">
                                <div class="pull-left">
                                    <span class="text-white">${subProcessMap.get("subProcess")}</span>
                                    <span class="text-white">${subProcessMap.get("activityProcess")}</span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3 ">
                                <p class="hint-text all-caps font-montserrat small no-margin ">
                                    Resueltas</p>
                                <p class="all-caps font-montserrat  no-margin text-success ">${subProcessMap.get("solvedCount")}</p>
                            </div>
                            <div class="col-md-3 text-center">
                                <p class="hint-text all-caps font-montserrat small no-margin ">Cierran
                                    Hoy</p>
                                <p class="all-caps font-montserrat  no-margin text-warning ">${subProcessMap.get("closeToday")}</p>
                            </div>
                            <div class="col-md-3 text-right">
                                <p class="hint-text all-caps font-montserrat small no-margin ">
                                    Incumplidas</p>
                                <p class="all-caps font-montserrat  no-margin text-danger ">${subProcessMap.get("nonCompletionCount")}</p>
                            </div>
                            <div class="col-md-3 text-right">
                                <p class="hint-text all-caps font-montserrat small no-margin ">En
                                    tiempo</p>
                                <p class="all-caps font-montserrat  no-margin text-success ">${subProcessMap.get("inTime")}</p>
                            </div>
                        </div>
                    </c:forEach>

                </div>
                <div class="p-t-10 full-width">
                    <a id="summarySubProcessButton" href="#" class="btn-circle-arrow b-grey"><i
                            class="pg-arrow_minimize text-danger"></i></a>
                    <span class="hint-text small">Ver más</span>
                </div>
            </security:authorize>
        </div>
    </div>
    <!-- END WIDGET -->
</div>
