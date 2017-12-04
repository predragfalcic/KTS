angular.module('app')

// Creating the Angular Controller

.controller('TenatInstitutionsController', function($http, $scope, AuthService) {

	var add = false;
	
	$scope.buttonText = 'Posalji';
	var init = function() {
		$http.get('api/tenat_institutions/' + AuthService.user.id).success(function(res) {
			$scope.institutions = res.institutions; // institutions
			$scope.failures = res.failures;
			$scope.failureForm.$setPristine();
			$scope.failure = null;
			$scope.institution = null;
			$scope.message='';
			$scope.buttonText = 'Posalji';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	$scope.initAdd = function(institution) {
		add = true;
		$scope.institution = institution;
		$scope.message='';
		$scope.buttonText = 'Posalji';
	};

	var addFailureInstitution = function(){
		var failureDTO = {'failure': $scope.failure, 'tenatId': AuthService.user.id, 'institutionId': $scope.institution.id};
		$http.post('api/tenat_institutions/failure/', failureDTO).success(function(res) {
			$scope.failure = null;
			$scope.institution = null;
			$scope.institutions = res.institutions; // institutions
			$scope.failures = res.failures;
			$scope.failureForm.$setPristine();
			$scope.message = "Azuriranje uspesno!";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};

	$scope.submit = function() {
		if(add){
			addFailureInstitution();
		}
	};
	init();
});