<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="victor.first.domain.User"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>

Hello 

<c:out value="${user.username}"/>


<form action="second" method="post">
<input type="text" name="nume"/>
<input type="text" name="age"/>

<input type="submit" value="Buton shmecher"> 
</form>

from JSP!
