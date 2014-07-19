<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html data-ng-app="testBoard">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Test Board project</title>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.19/angular.min.js" type="text/javascript"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.19/angular-route.min.js" type="text/javascript"></script>
  <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
  <link href="/resources/css/main.css" rel="stylesheet">
  
</head>
<body >
  <!-- navbar -->
  <div class="navbar navbar-inverse navbar-fixed-top" role="navigation" data-ng-include="'/resources/app/template/navbar.html'"></div>
  
  <div class="container-fluid">
    <div class="row">
      <!-- sidebar -->
      <div class="col-sm-3 col-md-2 sidebar" data-ng-include="'/resources/app/template/sidebar.html'"></div>
      <!-- main -->
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" role="main" data-ng-view></div>
    </div>
  </div>

  <script src="/resources/app/config/index.js"></script>
  <script src="/resources/app/controller/mainController.js"></script>
</body>
</html>
