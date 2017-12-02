angular.module('app')
// Creating the Angular Controller
.controller('HomeController', function($http, $scope, AuthService, $state) {
	$scope.user = AuthService.user;
	var userRole = AuthService.userRole;
	if(userRole.indexOf("ADMIN") > -1){
		$state.go("buildings");
	}else if(userRole.indexOf("STANAR") > -1){
		$state.go("profil");
	}
});