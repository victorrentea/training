<%@page import="java.util.Random"%>
<%@ page language="java" session="false"%>
<% String randomUsername="User"+new Random().nextInt(100); %>
<html>
<head>
<title>Spring Test Page - Logged Out</title>
</head>
<body>
	You are not logged in
	<p>
		<a href="home?login=true&username=<%=randomUsername%>">Login Now as <%=randomUsername%></a>
	<p>
		Timestamp:
		<%=new java.util.Date()%>
</body>
</html>