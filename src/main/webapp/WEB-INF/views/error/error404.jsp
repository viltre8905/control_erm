<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/template/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <%@include file="/WEB-INF/views/template/styles.jsp" %>
</head>

<body class="fixed-header error-page  ">
<%@include file="errorTemplate.jsp" %>

<!-- BEGIN JS -->
<%@include file="/WEB-INF/views/template/script.jsp" %>
<script>
    $('#error-title').text("Error 404");
    $('#error-message').text("El recurso solicitado no esta disponible.")
</script>
</body>
</html>