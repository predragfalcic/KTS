angular.module('app')

// Creating the Angular Controller

.controller('InstitutionsController', function($http, $scope, AuthService) {

	var edit = false;
	
	$scope.buttonText = 'Dodaj';
	$scope.institutionBuildings = [];
	var init = function() {
		$http.get('api/institutions').success(function(res) {
			$scope.institutions = res.institutions; // institutions
			$scope.buildings = res.buildings; // buildings
			$scope.institutionBuildings = []; // institution's buildings
			$scope.institutionForm.$setPristine();
			$scope.message='';
			$scope.institution = null;
			$scope.buttonText = 'Dodaj';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	$scope.initEdit = function(institution) {
		edit = true;
		$scope.institution = institution;
		$scope.institutionBuildings = institution.buildings;
		$scope.message='';
		$scope.buttonText = 'Azuriraj';
	};

	$scope.initAddInstitution = function() {
		edit = false;
		$scope.institution = null;
		$scope.institutionForm.$setPristine();
		$scope.message='';
		$scope.buttonText = 'Dodaj';
	};

	$scope.deleteInstitution = function(institution) {
		$http.delete('api/institutions/'+institution.id).success(function(res) {
			$scope.deleteMessage ="Uspesno!";
			init();
		}).error(function(error) {
			$scope.deleteMessage = error.message;
		});
	};

	var editInstitution = function(){
		$http.put('api/institutions/', $scope.institution).success(function(res) {
			$scope.institution = null;
			$scope.institutionForm.$setPristine();
			$scope.message = "Azuriranje uspesno!";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};

	var addInstitution = function(){
		$http.post('api/institutions/', $scope.institution).success(function(res) {
			$scope.institution = null;
			$scope.institutionForm.$setPristine();
			$scope.message = "Institucija kreirana";
			init();
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	$scope.submit = function() {
		if(edit){
			editInstitution();
		}else{
			addInstitution();	
		}
	};
	init();
});