<%@ page contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<body>
<%@ include file="menu.jsp" %>
<br />
	Hi, <b><c:out value="${user.fullName}" /></b>
	<h2>Employee List</h2>	
	<table border="1" style="border-collapse: collapse;">
		<tr>
			<th>Employee Name</th>
			<th>Phone</th>
			<th></th>
			<th></th>
		</tr>
		<c:forEach items="${employeeList}" var="employee">
			<tr>
				<td><c:out value="${employee.name}" /></td>
				<td><c:out value="${employee.phone}" /></td>
				<td><a href="${employee.id}">edit</a></td>
				<td width="30">
					<%--<security:authorize url="/employee/${employee.id}/delete">--%>
						<a href="${employee.id}/delete">delete</a>
					<%--</security:authorize>--%>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br /> 
	<input type="text" value="<c:out value='${param[\"name\"]}' />" id="nameFilter" />
	<input type="button" value="Filter by Name" onclick="javascript:window.location='?name='+document.getElementById('nameFilter').value;" />
	<br /><br />
	<%-- 
	You are logged in as "<security:authentication property="principal.username"/>".
	<a href="<c:url value="../j_spring_security_logout" />" > Logout</a>
	 --%>
	 <h3>Playground</h3>
	 <% 
System.out.println("Hello JSP world!");  
for (int i = 0; i < 10; i++ ) { 
    out.print("y");
    %><b>x</b><% 
    }
%>
<%=request.getParameter("param") %>
<%=request.getAttribute("employeeList") %>
</body>
</html>
