<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Spring Security Example </title>
</head>
<body>
<c:if test="${error}">
    <div>
        Invalid username and password.
    </div>
</c:if>
<c:if test="${logout}">
    <div>
        You have been logged out.
    </div>
</c:if>
<form action="/login" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><label> Remember me <input type="checkbox" name="remember-me" /></label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>
</body>
</html>