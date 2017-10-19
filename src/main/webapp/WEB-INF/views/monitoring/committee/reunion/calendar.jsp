<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 42);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calendario de reuniones</title>
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
</head>
<body class="fixed-header">
<%@include file="/WEB-INF/views/template/sideBar.jsp" %>
<!-- START PAGE-CONTAINER -->
<div class="page-container">

    <!-- START HEADER -->
    <%@include file="/WEB-INF/views/template/navBar.jsp" %>
    <!-- END HEADER -->
    <div class="page-content-wrapper full-height">
        <div id="calendarContainer" class="content full-height p-t-10">
            <div class="windows">
                <h5 class="">
                    Calendario de Reuniones
                </h5>
            </div>
            <!-- START CALENDAR -->
            <div class="calendar m-t-5">
                <!-- START CALENDAR HEADER-->
                <div class="calendar-header">
                    <div class="drager">
                        <div class="years" id="years"></div>
                    </div>
                </div>
                <div class="options">
                    <div class="months-drager drager">
                        <div class="months" id="months"></div>
                    </div>
                    <h4 class="semi-bold date" id="currentDate">&amp;</h4>
                    <div class="drager week-dragger">
                        <div class="weeks-wrapper" id="weeks-wrapper">
                        </div>
                    </div>
                </div>
                <!-- START CALENDAR GRID-->
                <div id="calendar" class="calendar-container">
                </div>
                <!-- END CALENDAR GRID-->
            </div>
            <!-- END CALENDAR -->

        </div>

    </div>
</div>
<!-- START Calendar Events Form -->
<div class="quickview-wrapper calendar-event" id="calendar-event">
    <div class="view-port clearfix" id="eventFormController">
        <div class="view bg-white">
            <div class="scrollable">
                <div class="p-l-30 p-r-30 p-t-20">
                    <a class="pg-close text-master link pull-right"
                       data-toggle="quickview" data-toggle-element="#calendar-event" href="#"></a>
                    <h4 id="event-date">&amp;
                    </h4>
                    <div class="m-b-20">
                        <i class="fa fa-clock-o"></i>
                        <span id="lblfromTime"></span> a
                        <span id="lbltoTime"></span>
                        <div class="pull-right hidden" id="progress-bar-calendar">
                            <div class="col-sm-1 text-center center-scale">
                                <div class="progress-circle-indeterminate m-t-0">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="modalMessage"></div>
                </div>
                <div class="p-t-15">
                    <input id="eventIndex" name="eventIndex" type="hidden">
                    <div class="form-group-attached">
                        <div class="form-group form-group-default">
                            <label>Título</label>
                            <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                <input type="text" class="form-control" id="title" name=""
                                       placeholder="Título de la reunión">
                            </security:authorize>
                            <security:authorize access="hasRole('ROLE_COMMITTEE_MEMBER')">
                                <p id="title"></p>
                            </security:authorize>
                        </div>
                        <div class="row clearfix">
                            <div class="col-sm-12">
                                <div class="form-group form-group-default">
                                    <label>Lugar</label>
                                    <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                        <input type="text" class="form-control" id="place"
                                               placeholder="Lugar de la reunión" name="">
                                    </security:authorize>
                                    <security:authorize access="hasRole('ROLE_COMMITTEE_MEMBER')">
                                        <p id="place"></p>
                                    </security:authorize>
                                </div>
                            </div>
                        </div>
                        <div class="row clearfix">
                            <div class="col-sm-12">
                                <div class="form-group form-group-default">
                                    <label>Ubicación</label>
                                    <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                                    <textarea class="form-control" id="ubication"
                                                              placeholder="Ubicación física del documento o minuta"
                                                              name="ubication"></textarea>
                                    </security:authorize>
                                    <security:authorize access="hasRole('ROLE_COMMITTEE_MEMBER')">
                                        <p id="ubication"></p>
                                    </security:authorize>
                                </div>
                            </div>
                        </div>
                        <div class="row clearfix">
                            <div class="col-sm-12">
                                <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                <div class="fileinput fileinput-new" data-provides="fileinput"
                                     style="width: 100%;">
                                    <div id="fileName" class="fileinput-filename p-l-10 p-t-25"
                                         style="border: 1px solid rgba(0, 0, 0, 0.07); height: 50px;"></div>
                                    <div class="m-t-5">
                                            <span class="btn default btn-file">
                                            <span class="fileinput-new">Subir Archivo</span>
											<span class="fileinput-exists">Cambiar </span>
											<input id="file" name="..." type="file" class=""></span>
                                        <a href="#" class="btn default fileinput-exists"
                                           data-dismiss="fileinput">
                                            Eliminar </a>
                                        </security:authorize>
                                        <a id="download" class='btn btn-success m-l-10'
                                           type="button"
                                           data-toggle="tooltip"
                                           data-original-title="Descargar"
                                           href="#"><i
                                                class="pg-download"></i>
                                        </a>
                                        <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                                    </div>
                                </div>
                                </security:authorize>
                            </div>
                        </div>
                    </div>
                </div>
                <security:authorize access="hasRole('ROLE_SECRETARY_COMMITTEE')">
                    <div class="p-l-30 p-r-30 p-t-30">
                        <button id="eventSave" class="btn btn-warning btn-cons">Guardar Datos</button>
                        <button id="eventDelete" class="btn btn-white"><i class="fa fa-trash-o"></i>
                        </button>
                    </div>
                </security:authorize>
            </div>
        </div>
    </div>
</div>
<!-- END Calendar Events Form -->

<!-- BEGIN JS -->
<%@include file="/WEB-INF/views/template/script.jsp" %>
<script src="${pageContext.request.contextPath}/resources/plugins/moment/moment.min.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/moment/moment-with-locales.min.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/hammer.min.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/pages.calendar.min.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/calendar.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/monitoring/committee/general_committee.js"
        type="text/javascript"></script>
<!-- END JS -->


</body>
</html>