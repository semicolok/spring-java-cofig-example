<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Login</title>
</head>
<body>

  <h2>User Login Form</h2>
  <form:form id="loginForm" method="post" action="/d/login" role="form">
    <input type="text" id="username" name="username" />
    <input type="password" id="password" name="password" /> 
    <input type="submit"  value="Login">
  </form:form>
</body>
</html>