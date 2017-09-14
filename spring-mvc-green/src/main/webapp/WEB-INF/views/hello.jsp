<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="victor.first.domain.User"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>
<html>


<body>
<%@ include file="menu.jsp" %>

Hello 

<%
//if (true) throw new RuntimeException("mock");
%>

<form action="second" method="post">
<input type="text" name="nume"/>
<input type="text" name="age"/>

<input type="submit" value="Buton shmecher"> 
</form>

from JSP!
</body>
</html>