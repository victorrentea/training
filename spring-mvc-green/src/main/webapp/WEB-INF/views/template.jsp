<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>
	<t:insertAttribute name="title"/>
	<t:importAttribute  name="scripts" />
	
</title>
	<c:forEach items="${scripts}" var="scriptName">
		<script type="text/javascript" src="<c:url value="/js/${scriptName}"/>"></script>
	</c:forEach>
</head>
<body>
	<div id="header">
		<t:insertAttribute name="header"/>
	</div>
	<div id="body">
		<t:insertAttribute name="body"/>
	</div>
	<div id="footer">
		<t:insertAttribute name="footer"/>
	</div>
</body>

</html>