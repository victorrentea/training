<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="spring.model.Employee" %>
<html>
<body>
	<h2 title="<%=((Employee)request.getAttribute("employee")).getName()%>">
		Edit Employee <c:out value="${employee.name }" />
	</h2>
	<sf:form method="POST" modelAttribute="employee">
		Name:<sf:input path="name" />
		<br />
		Phone:<sf:input path="phone" />
		<br />
		
		<a href=".">Back to List</a>
		<input type="submit" value="Save" />
	</sf:form>
</body>
</html>
