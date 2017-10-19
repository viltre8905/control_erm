<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% session.setAttribute("sideBar", 37);%>
<%@include file="/WEB-INF/views/template/activity.jsp" %>
<security:authorize
        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY', 'ROLE_EXECUTER')">
    <controlerm:hasNotRole
            role="ROLE_SUBPROCESS_SUPERVISORY,ROLE_EXECUTER">
        <script>
            $('#myActivitiesContainer').empty();
        </script>
    </controlerm:hasNotRole>
</security:authorize>
</div>
</body>
</html>



