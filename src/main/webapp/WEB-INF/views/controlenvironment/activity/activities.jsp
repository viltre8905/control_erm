<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 14);%>
<%@include file="/WEB-INF/views/template/activity.jsp" %>
<security:authorize
        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_EXECUTER','ROLE_ACTIVITY_RESPONSIBLE')">
    <controlerm:hasNotRole
            role="ROLE_SUBPROCESS_SUPERVISORY,ROLE_EXECUTER,ROLE_ACTIVITY_RESPONSIBLE">
        <script>
            $('#myActivitiesContainer').empty();
        </script>
    </controlerm:hasNotRole>
</security:authorize>
</div>
</body>
</html>

