<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<% session.setAttribute("sideBar", 0);%>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<security:authorize
        access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')">
    <c:redirect url="/admin/user/all"></c:redirect>
</security:authorize>
<security:authorize
        access="hasRole('ROLE_GENERAL_SUPERVISORY')">
    <c:redirect url="/supervisoryHome"></c:redirect>
</security:authorize>
<controlerm:hasOnlyRole
        role="ROLE_EXECUTER">
    <c:redirect url="/control-environment/activity/activities"></c:redirect>
</controlerm:hasOnlyRole>
<controlerm:hasNotRole
        role="ROLE_PROCESS_SUPERVISORY,ROLE_SUBPROCESS_SUPERVISORY">
    <c:redirect url="/control-environment/activity/activities"></c:redirect>
</controlerm:hasNotRole>
<%@include file="/WEB-INF/views/template/homeBody.jsp" %>
