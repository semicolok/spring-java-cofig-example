var app = angular.module("testBoard", ['ngRoute']);

app.config(function($routeProvider){
   $routeProvider
     .when('/', {
        redirectTo: '/main'
     })
     .when('/main', {
	 controller : 'MainCtrl',
	 templateUrl : '/resources/app/template/main.html'
     });
});