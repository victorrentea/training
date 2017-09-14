<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="victor.first.domain.User"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>

<form action="" method="post">
<input type="text" name="username" placeholder="username"/>
<input type="text" name="firstName" placeholder="firstName"/>
<input type="text" name="lastName" placeholder="lastName"/>

<input type="submit" value="Creaza Gigi!"> 

<a href="<c:url value="/bani"/>">BACK</a>
</form>
