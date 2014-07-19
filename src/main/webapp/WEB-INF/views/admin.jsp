<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<c:url value="/d/signout" var="logoutUrl" />
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>
 
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>Welcome : ${pageContext.request.userPrincipal.name} 
                 | <a href="${logoutUrl}" > Logout</a></h2>  
	</c:if>
</body>
</html>