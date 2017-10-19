<security:authorize
        access="hasAnyRole('ROLE_PROCESS_SUPERVISORY','ROLE_SUBPROCESS_SUPERVISORY','ROLE_EXECUTER','ROLE_GENERAL_SUPERVISORY','ROLE_ACTIVITY_RESPONSIBLE')">
    <select class="m-l-10 inline" id="processSelect" style="width: 200px" data-init-plugin="select2"
            onchange="onProcessChange(this,'${pageContext.request.contextPath}/setProcessSelected')">
        <c:choose>
            <c:when test="${processSelected!=null}">

                <c:forEach var="process" items="${processes}">
                    <c:choose>
                        <c:when test="${process.id==processSelected}">
                            <option value="${process.id}" selected="selected">${process.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${process.id}">${process.name}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:when><c:otherwise>
            <c:forEach var="process" items="${processes}">
                <option value="${process.id}">${process.name}</option>
            </c:forEach>
        </c:otherwise>
        </c:choose>
    </select>
</security:authorize>
<!-- END NOTIFICATIONS LIST -->