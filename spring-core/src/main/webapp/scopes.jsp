<%@page import="org.springframework.context.ApplicationContext"%>
<%@page language="java"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%ApplicationContext spring = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());%>
<html>
<head>
<title>Spring Test Page - Scopes</title>
</head>
<body>
		<h2>Scopes</h2>
		<ul>
		<li><b>singleton</b> instance: <i><%=spring.getBean("userService")%></i></li>
		<li><b>session</b> instance: <i><%=spring.getBean("userDetails")%></i></li>
		<li><b>request</b> instance: <i><%=spring.getBean("requestScopedBean")%></i></li>
		<li><b>prototype</b> instance: <i><%=spring.getBean("prototypeScopedBean")%></i></li>
		</ul>
</body>
</html>