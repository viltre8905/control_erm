<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!-- START HEADER -->
<div class="header ">
    <!-- START MOBILE CONTROLS -->
    <!-- LEFT SIDE -->
    <div class="pull-left full-height visible-sm visible-xs">
        <!-- START ACTION BAR -->
        <div class="sm-action-bar">
            <a href="#" class="btn-link toggle-sidebar" data-toggle="sidebar">
                <span class="icon-set menu-hambuger"></span>
            </a>
        </div>
        <!-- END ACTION BAR -->
    </div>
    <!-- RIGHT SIDE -->
    <!-- END MOBILE CONTROLS -->
    <div class=" pull-left sm-table">
        <div class="header-inner">
            <div class="brand inline">
                <a href="${pageContext.request.contextPath}/home">
                    <img src="${pageContext.request.contextPath}/resources/img/logo.png" alt="logo"
                         data-src="${pageContext.request.contextPath}/resources/img/logo.png"
                         data-src-retina="${pageContext.request.contextPath}/resources/img/logo.png" width="140"
                         height="30">
                </a>
            </div>
            <!-- START NOTIFICATION LIST -->
            <ul class="notification-list no-margin hidden-sm hidden-xs b-grey b-l b-r no-style p-l-30 p-r-20">

                <li class="p-r-15 inline">
                    <div class="dropdown">
                        <a id="notification-drop-down" href="javascript:;" id="notification-center"
                           class="icon-set globe-fill"
                           data-toggle="dropdown">
                            <c:if test="${notifications.size()>0}">
                                <span class="badge badge-danger" style="position: absolute; top:-6px; left: 10px">${notifications.size()}</span>
                            </c:if>
                        </a>
                        <!-- START Notification Dropdown -->
                        <div class="dropdown-menu notification-toggle" role="menu"
                             aria-labelledby="notification-center">
                            <!-- START Notification -->
                            <div class="notification-panel">
                                <!-- START Notification Body-->
                                <div id="notification-body" class="notification-body scrollable">
                                    <c:forEach var="notification" items="${notifications}">
                                        <!-- START Notification Item-->
                                        <div class="notification-item <c:if test="${!notification.read}">unread</c:if> clearfix">
                                            <!-- START Notification Item-->
                                            <div class="heading">
                                                <a href="${pageContext.request.contextPath}${notification.path}"
                                                   class="text-${notification.type}">
                                                    <span class="bold">${notification.title}</span>
                                                    <span class="fs-12 m-l-10">${notification.sender.firstName} ${notification.sender.lastName}</span>
                                                </a>
                                                <div class="pull-right">
                                                    <div class="thumbnail-wrapper d16 circular inline m-t-15 m-r-10 toggle-more-details">
                                                        <div><i class="fa fa-angle-left"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="more-details">
                                                    <div class="more-details-inner">
                                                        <h5 class="semi-bold fs-16">
                                                                ${notification.body}
                                                        </h5>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- END Notification Item-->
                                            <!-- START Notification Item Right Side-->
                                            <div class="option" data-toggle="tooltip" data-placement="left"
                                                 title="<c:if test="${notification.read}">Marcar como no leída</c:if>
                                                 <c:if test="${!notification.read}">Marcar como leída</c:if>">
                                                <a href="#" class="mark notification-mark"
                                                   onclick="changeNotificationState(this,'${pageContext.request.contextPath}')"></a>
                                                <input type="text" hidden="true" value="${notification.id}">
                                            </div>
                                            <!-- END Notification Item Right Side-->
                                        </div>
                                        <!-- START Notification Body-->
                                    </c:forEach>
                                </div>
                                <!-- END Notification Body-->
                                <!-- START Notification Footer-->
                                <div id="notification-footer" class="notification-footer text-center">
                                    <div id="progress-bar-notification" class="progress hidden">
                                        <div class="progress-bar-indeterminate">
                                        </div>
                                    </div>
                                    <c:choose>
                                        <c:when test="${notifications.size()>0}">
                                            <a id="delete-notification" href="#" class=""
                                               onclick="deleteNotification('${pageContext.request.contextPath}');">Eliminar
                                                todas las notificaciones</a>
                                        </c:when>
                                        <c:otherwise>
                                            <p>No hay notificaciones</p>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                                <!-- START Notification Footer-->
                            </div>
                            <!-- END Notification -->
                        </div>
                        <!-- END Notification Dropdown -->
                    </div>
                </li>
                <security:authorize
                        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_EXECUTER','ROLE_GENERAL_SUPERVISORY')">
                    <li class="p-r-15 inline">
                        <a id="entityDataLink" href="#" class="fa fa-info"
                           data-original-title="Datos de la entidad" data-toggle="tooltip"
                           data-placement="bottom"></a>
                    </li>
                </security:authorize>
                <li class="p-r-15 inline">
                    <a id="profileLink" href="#" onclick="fillProfileModal('${pageContext.request.contextPath}');"
                       class="fa fa-user"
                       data-original-title="Perfil" data-toggle="tooltip" data-placement="bottom"></a>
                </li>
                <li class="p-r-15 inline">
                    <a id="changePasswordLink" href="#" class="fa fa-key"
                       data-original-title="Cambiar contrase&ntilde;a" data-toggle="tooltip"
                       data-placement="bottom"></a>
                </li>
            </ul>