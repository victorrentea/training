<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="victor.training.spring.model.Employee" %>
<html>
<head>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
	<h2 title="<%=((Employee)request.getAttribute("employee")).getName()%>">
		Edit Employee <c:out value="${employee.name }" />
	</h2>
	<sf:form method="POST" modelAttribute="employee">
		<sf:label path="name" cssErrorClass="error">Name:</sf:label>
        <sf:input path="name" cssErrorClass="error"/>
        <sf:errors path="name" cssClass="error"/>
        <br />
        <sf:label path="phone" cssErrorClass="error">Phone:</sf:label>
        <sf:input path="phone" cssErrorClass="error" />
        <sf:errors path="phone" cssClass="error"/>
        <br />

		<a href=".">Back to List</a>

		<input type="submit" value="Save" />
	</sf:form>
</body>
</html>
