<%@page import="victor.training.spring.webapp.PrototypeStatefulBean"%>
<%@page import="victor.training.spring.webapp.RequestBean"%>
<%@page import="victor.training.spring.webapp.UserSession"%>
<%@page import="victor.training.spring.webapp.UserServiceSingleton"%>
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
		<li><b>singleton</b> instance: <i><%=spring.getBean(UserServiceSingleton.class)%></i></li>
		<li><b>session</b> instance: <i><%=spring.getBean(UserSession.class)%></i></li>
		<li><b>request</b> instance: <i><%=spring.getBean(RequestBean.class)%></i></li>
		<li><b>prototype</b> instance: <i><%=spring.getBean(PrototypeStatefulBean.class)%></i></li>
		<li><b>prototype injected into singleton</b> instance: <i><%=spring.getBean(UserServiceSingleton.class).testInjectedPrototype()%></i></li>
		<li><b>prototype got via Provider from singleton</b> instance: <i><%=spring.getBean(UserServiceSingleton.class).testProvidedPrototype()%></i></li>
		<li><b>prototype got via ObjectFactory from singleton</b> instance: <i><%=spring.getBean(UserServiceSingleton.class).testFactoryPrototype()%></i></li>
		</ul>
</body>
</html>