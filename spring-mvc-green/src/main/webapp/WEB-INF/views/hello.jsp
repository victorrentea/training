<%@page import="first.domain.User"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>
<html>


<body>
Hello 

<%=((User)request.getAttribute("user")).getUsername() %>
from JSP!
</body>
</html>