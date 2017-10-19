<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% Object sessionAttribute = session.getAttribute("sideBar");
    int sideBar = -1;
    if (sessionAttribute != null) {
        sideBar = (Integer) sessionAttribute;
    }
%>


<nav class="page-sidebar" data-pages="sidebar">
    <!-- BEGIN SIDEBAR MENU HEADER-->
    <div class="sidebar-header">
        <img src="${pageContext.request.contextPath}/resources/img/logo_white.png" alt="logo" class="brand"
             data-src="${pageContext.request.contextPath}/resources/img/logo_white.png"
             data-src-retina="${pageContext.request.contextPath}/resources/img/logo_white.png" width="140"
             height="30">
        <div class="sidebar-header-controls">
            <button id="pinButton" type="button" class="btn btn-link visible-lg-inline" data-toggle-pin="sidebar"
                    onclick="pinButton();"><i
                    class=" fa fs-12"></i>
            </button>
        </div>
    </div>
    <!-- END SIDEBAR MENU HEADER-->
    <!-- START SIDEBAR MENU -->
    <div class="sidebar-menu">
        <!-- BEGIN SIDEBAR MENU ITEMS-->
        <ul class="menu-items">
            <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')">
                <li class="<% if(sideBar>=1&&sideBar<=9){%>active open<%}%>">
                    <a href="javascript:;"><span class="title">Admin</span>
                        <span class="arrow"></span></a>
                    <span class="icon-thumbnail <% if(sideBar>=1&&sideBar<=9){%>bg-success<%}%>"><i
                            class="fa fa-dashboard"></i></span>
                    <ul class="sub-menu">
                        <security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                            <li class="<% if(sideBar>=1&&sideBar<=4){%>active open<%}%>">
                                <a href="javascript:;"><span class="title">Entidades</span>
                                    <span class=" arrow"></span></a>
                                <span class="icon-thumbnail "
                                      <% if(sideBar>=1&&sideBar<=4){%>style="background-color: #6d5cae"<%}%>><i
                                        class="fa fa-institution"></i></span>
                                <ul class="sub-menu" <% if(sideBar<3||sideBar>4){%>style="display:none;"<%}%> >
                                    <li class="<% if(sideBar==3){%>active<%}%>">
                                        <a href="${pageContext.request.contextPath}/admin/entity/entities">Datos
                                            Generales</a>
                            <span class="icon-thumbnail" <% if(sideBar==3){%>style="background-color: #933432"<%}%>><i
                                    class="fa fa-info"></i></span>
                                    </li>
                                    <li class="<% if(sideBar==4){%>active<%}%>">
                                        <a href="${pageContext.request.contextPath}/admin/entity/structure">Estructura</a>
                            <span class="icon-thumbnail" <% if(sideBar==4){%>style="background-color: #933432"<%}%>><i
                                    class="fa fa-cubes"></i></span>
                                    </li>
                                </ul>
                            </li>
                        </security:authorize>
                        <li class="<% if(sideBar==5){%>active<%}%>">
                            <a href="${pageContext.request.contextPath}/admin/user/all">Usuarios</a>
                            <span class="icon-thumbnail" <% if(sideBar==5){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-users"></i></span>
                        </li>
                        <li class="<% if(sideBar==6){%>active<%}%>">
                            <a href="${pageContext.request.contextPath}/admin/nomenclature/all">Nomencladores</a>
                            <span class="icon-thumbnail"
                                  <% if(sideBar==6){%>style="background-color: #6d5cae"<%}%>>Nc</span>
                        </li>
                        <security:authorize access="hasRole('ROLE_ADMIN')">
                            <li class="<% if(sideBar==7){%>active<%}%>">
                                <a href="${pageContext.request.contextPath}/admin/process/processes">Procesos</a>
                            <span class="icon-thumbnail" <% if(sideBar==7){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-gears"></i></span>
                            </li>
                            <li class="<% if(sideBar==8){%>active<%}%>">
                                <a href="${pageContext.request.contextPath}/admin/guide/guides">Cuestionarios</a>
                            <span class="icon-thumbnail" <% if(sideBar==8){%>style="background-color: #6d5cae"<%}%>><i
                                    class="pg-note"></i></span>
                            </li>
                            <li class="<% if(sideBar==9){%>active<%}%>">
                                <a href="${pageContext.request.contextPath}/admin/internal-control/processes">Control Interno</a>
                            <span class="icon-thumbnail" <% if(sideBar==9){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-play"></i></span>
                            </li>
                        </security:authorize>
                        <security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                            <li class="<% if(sideBar==10){%>active<%}%>">
                                <a href="${pageContext.request.contextPath}/admin/task/tasks">Tareas</a>
                            <span class="icon-thumbnail" <% if(sideBar==10){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-tasks"></i></span>
                            </li>
                        </security:authorize>
                    </ul>
                </li>
            </security:authorize>
            <security:authorize
                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_EXECUTER','ROLE_ACTIVITY_RESPONSIBLE')">
                <security:authorize
                        access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY')">
                    <li class="">
                        <a href="${pageContext.request.contextPath}/home">
                            <span class="title">Inicio</span>
                        </a>
                        <span class="icon-thumbnail <% if(sideBar==0){%>bg-success<%}%>"><i class="pg-home"></i></span>
                    </li>
                </security:authorize>
                <li class="<% if(sideBar>=11&&sideBar<=14){%>active open<%}%>">
                    <a href="javascript:;" data-toggle="tooltip"
                       data-original-title="Entorno de Control">
                        <span class="title">E.C.</span>
                        <span class="arrow "></span> </a>
                    <span class="icon-thumbnail <% if(sideBar>=11&&sideBar<=14){%>bg-success<%}%>"><i
                            class="fa fa-globe"></i></span>
                    <ul class="sub-menu">
                        <security:authorize
                                access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY')">
                                <li class="<% if(sideBar==11){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/control-environment/dashboard/info">Informaci&oacute;n
                                        General</a>
                            <span class="icon-thumbnail " <% if(sideBar==11){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-line-chart"></i></span>
                                </li>
                                <li class="<% if(sideBar==12){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/control-environment/process/objectives">Objetivos</a>
                            <span class="icon-thumbnail" <% if(sideBar==12){%>style="background-color: #6d5cae"<%}%>><i
                                    class="pg-ordered_list"></i></span>
                                </li>
                            </security:authorize>
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                                <li class="<% if(sideBar==13){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/control-environment/guide/guides">Cuestionarios</a>
                            <span class="icon-thumbnail" <% if(sideBar==13){%>style="background-color: #6d5cae"<%}%>><i
                                    class="pg-note"></i></span>
                                </li>
                            </security:authorize>
                        </security:authorize>
                        <li class="<% if(sideBar==14){%>active<%}%>">
                            <a href="${pageContext.request.contextPath}/control-environment/activity/activities">Plan de
                                Medidas</a>
                            <span class="icon-thumbnail" <% if(sideBar==14){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-table"></i></span>
                        </li>
                    </ul>
                </li>
                <li class="<% if(sideBar>=17&&sideBar<=22){%>active open<%}%>">
                    <a href="javascript:;" data-toggle="tooltip"
                       data-original-title="Evaluaci&oacute;n de Riesgo"><span class="title">E.R.</span>
                        <span class=" arrow"></span></a>
                    <span class="icon-thumbnail <% if(sideBar>=17&&sideBar<=22){%>bg-success<%}%>"><i
                            class="fa fa-warning"></i></span>
                    <ul class="sub-menu">
                        <security:authorize
                                access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY')">
                                <li class="<% if(sideBar==20){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/risk/dashboard/info">Informaci&oacute;n
                                        General</a>
                            <span class="icon-thumbnail " <% if(sideBar==20){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-line-chart"></i></span>
                                </li>
                                <li class="<% if(sideBar==19){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/risk/management/identification">Identificación</a>
                            <span class="icon-thumbnail " <% if(sideBar==19){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-info"></i></span>
                                </li>
                            </security:authorize>
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                                <li class="<% if(sideBar==21){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/risk/guide/guides">Cuestionarios</a>
                            <span class="icon-thumbnail" <% if(sideBar==21){%>style="background-color: #6d5cae"<%}%>><i
                                    class="pg-note"></i></span>
                                </li>
                            </security:authorize>
                        </security:authorize>
                        <li class="<% if(sideBar==22){%>active<%}%>">
                            <a href="${pageContext.request.contextPath}/risk/activity/activities">Plan de Medidas</a>
                            <span class="icon-thumbnail" <% if(sideBar==22){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-table"></i></span>
                        </li>
                    </ul>
                </li>
                <li class="<% if(sideBar>=23&&sideBar<=29){%>active open<%}%>">
                    <a href="javascript:;" data-toggle="tooltip"
                       data-original-title="Actividades de Control"><span class="title">A.C.</span>
                        <span class=" arrow"></span></a>
                    <span class="icon-thumbnail <% if(sideBar>=23&&sideBar<=29){%>bg-success<%}%>"><i
                            class="fa fa-eye"></i></span>
                    <ul class="sub-menu">
                        <security:authorize
                                access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY')">
                                <li class="<% if(sideBar==27){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/control-activity/dashboard/info">Informaci&oacute;n
                                        General</a>
                            <span class="icon-thumbnail " <% if(sideBar==27){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-line-chart"></i></span>
                                </li>
                            </security:authorize>
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                                <li class="<% if(sideBar==28){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/control-activity/guide/guides">Cuestionarios</a>
                            <span class="icon-thumbnail" <% if(sideBar==28){%>style="background-color: #6d5cae"<%}%>><i
                                    class="pg-note"></i></span>
                                </li>
                            </security:authorize>
                        </security:authorize>
                        <li class="<% if(sideBar==29){%>active<%}%>">
                            <a href="${pageContext.request.contextPath}/control-activity/activity/activities">Plan de
                                Medidas</a>
                            <span class="icon-thumbnail" <% if(sideBar==29){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-table"></i></span>
                        </li>
                    </ul>
                </li>
                <li class="<% if(sideBar>=30&&sideBar<=37){%>active open<%}%>">
                    <a href="javascript:;" data-toggle="tooltip"
                       data-original-title="Informaci&oacute;n y Comunicaci&oacute;n"><span class="title">I.C.</span>
                        <span class=" arrow"></span></a>
                    <span class="icon-thumbnail <% if(sideBar>=30&&sideBar<=37){%>bg-success<%}%>"><i
                            class="fa fa-bell-o"></i></span>
                    <ul class="sub-menu">
                        <security:authorize
                                access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY')">
                                <li class="<% if(sideBar==35){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/info-and-com/dashboard/info">Informaci&oacute;n
                                        General</a>
                            <span class="icon-thumbnail " <% if(sideBar==35){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-line-chart"></i></span>
                                </li>
                            </security:authorize>
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                                <li class="<% if(sideBar==36){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/info-and-com/guide/guides">Cuestionarios</a>
                            <span class="icon-thumbnail" <% if(sideBar==36){%>style="background-color: #6d5cae"<%}%>><i
                                    class="pg-note"></i></span>
                                </li>
                            </security:authorize>
                        </security:authorize>
                        <li class="<% if(sideBar==37){%>active<%}%>">
                            <a href="${pageContext.request.contextPath}/info-and-com/activity/activities">Plan de
                                Medidas</a>
                            <span class="icon-thumbnail" <% if(sideBar==37){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-table"></i></span>
                        </li>
                    </ul>
                </li>
            </security:authorize>
            <security:authorize
                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_EXECUTER',
                    'ROLE_COMMITTEE_MEMBER','ROLE_SECRETARY_COMMITTEE','ROLE_ACTIVITY_RESPONSIBLE')">
                <li class="<% if(sideBar>=38&&sideBar<=46){%>active open<%}%>">
                    <a href="javascript:;" data-toggle="tooltip"
                       data-original-title="Supervisi&oacute;n y Monitoreo"><span class="title">S.M.</span>
                        <span class=" arrow"></span></a>
                    <span class="icon-thumbnail <% if(sideBar>=38&&sideBar<=46){%>bg-success<%}%>"><i
                            class="fa fa-search"></i></span>
                    <ul class="sub-menu">
                        <security:authorize
                                access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY')">
                                <li class="<% if(sideBar==38){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/monitoring/dashboard/info">Informaci&oacute;n
                                        General</a>
                            <span class="icon-thumbnail " <% if(sideBar==38){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-line-chart"></i></span>
                                </li>
                            </security:authorize>
                            <security:authorize
                                    access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
                                <li class="<% if(sideBar==39){%>active<%}%>">
                                    <a href="${pageContext.request.contextPath}/monitoring/guide/guides">Cuestionarios</a>
                            <span class="icon-thumbnail" <% if(sideBar==39){%>style="background-color: #6d5cae"<%}%>><i
                                    class="pg-note"></i></span>
                                </li>
                            </security:authorize>
                        </security:authorize>
                        <security:authorize
                                access="hasAnyRole('ROLE_GENERAL_SUPERVISORY','ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_EXECUTER','ROLE_ACTIVITY_RESPONSIBLE')">
                            <li class="<% if(sideBar==40){%>active<%}%>">
                                <a href="${pageContext.request.contextPath}/monitoring/activity/activities">Plan de
                                    Medidas</a>
                            <span class="icon-thumbnail" <% if(sideBar==40){%>style="background-color: #6d5cae"<%}%>><i
                                    class="fa fa-table"></i></span>
                            </li>
                        </security:authorize>
                        <security:authorize
                                access="hasAnyRole('ROLE_COMMITTEE_MEMBER','ROLE_SECRETARY_COMMITTEE')">
                            <li class="<% if(sideBar>=41&&sideBar<=46){%>active open<%}%>">
                                <a href="javascript:;"><span class="title">Comité</span>
                                    <span class=" arrow"></span></a>
                                <span class="icon-thumbnail "
                                      <% if(sideBar>=41&&sideBar<=46){%>style="background-color: #6d5cae"<%}%>><i
                                        class="fa fa-th-large"></i></span>
                                <ul class="sub-menu" <% if(sideBar<41||sideBar>46){%>style="display:none;"<%}%>>
                                    <li class="<% if(sideBar==41){%>active<%}%>">
                                        <a href="${pageContext.request.contextPath}/monitoring/committee/member/all">Miembros</a>
                                    <span class="icon-thumbnail "
                                          <% if(sideBar==41){%>style="background-color: #933432"<%}%>><i
                                            class="fa fa-users"></i></span>
                                    </li>
                                    <li class="<% if(sideBar==44){%>active<%}%>">
                                        <a href="${pageContext.request.contextPath}/monitoring/committee/thematic-plan/themes">Plan
                                            Temático</a>
                                    <span class="icon-thumbnail "
                                          <% if(sideBar==44){%>style="background-color: #933432"<%}%>><i
                                            class="fa fa-book"></i></span>
                                    </li>
                                    <li class="<% if(sideBar==42){%>active<%}%>">
                                        <a href="${pageContext.request.contextPath}/monitoring/committee/reunion/calendar">Reuniones</a>
                                    <span class="icon-thumbnail "
                                          <% if(sideBar==42){%>style="background-color: #933432"<%}%>><i
                                            class="fa fa-calendar"></i></span>
                                    </li>
                                    <li class="<% if(sideBar==43){%>active<%}%>">
                                        <a href="${pageContext.request.contextPath}/monitoring/committee/report/all">Informes</a>
                                    <span class="icon-thumbnail "
                                          <% if(sideBar==43){%>style="background-color: #933432"<%}%>><i
                                            class="pg-form"></i></span>
                                    </li>
                                    <li class="<% if(sideBar==45){%>active<%}%>">
                                        <a href="${pageContext.request.contextPath}/monitoring/committee/risk/preventionPlan">Plan
                                            de Prevención</a>
                                    <span class="icon-thumbnail "
                                          <% if(sideBar==45){%>style="background-color: #933432"<%}%>><i
                                            class="fa fa-warning"></i></span>
                                    </li>
                                    <li class="<% if(sideBar==46){%>active<%}%>">
                                        <a href="${pageContext.request.contextPath}/monitoring/committee/entity/measuresPlan">Plan
                                            de Medidas de la Entidad</a>
                                    <span class="icon-thumbnail "
                                          <% if(sideBar==46){%>style="background-color: #933432"<%}%>><i
                                            class="fa fa-warning"></i></span>
                                    </li>
                                </ul>
                            </li>
                        </security:authorize>
                    </ul>
                </li>
            </security:authorize>
        </ul>
        <div class="clearfix"></div>
    </div>
    <!-- END SIDEBAR MENU -->
</nav>