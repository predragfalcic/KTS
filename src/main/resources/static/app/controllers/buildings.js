angular.module('app')

// Creating the Angular Controller

.controller('BuildingsController', function($http, $scope, AuthService) {

	var edit = false;
	
	$scope.buttonText = 'Dodaj';
	
	var init = function() {
		$http.get('api/buildings').success(function(res) {
			$scope.buildings = res.buildings; // Buildings
			$scope.freeTenats = res.tenats; // Tenats without building
			$scope.buildingForm.$setPristine();
			$scope.message='';
			$scope.building = null;
			$scope.institutions = null;
			$scope.buttonText = 'Dodaj';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	$scope.initEdit = function(building) {
		edit = true;
		$scope.building = building;
		$scope.institutions = building.institutions;
		$scope.message='';
		$scope.buttonText = 'Azuriraj';
	};

	$scope.initAddBuilding = function() {
		edit = false;
		$scope.building = null;
		$scope.buildingForm.$setPristine();
		$scope.message='';
		$scope.institutions = null;
		$scope.buttonText = 'Dodaj';
	};

	$scope.deleteBuilding = function(building) {
		$http.delete('api/buildings/'+building.id).success(function(res) {
			$scope.deleteMessage ="Uspesno!";
			init();
		}).error(function(error) {
			$scope.deleteMessage = error.message;
		});
	};
	
	// Brisemo instituciju iz zgrade
	$scope.deleteInstitutionFromBuilding = function(institution) {
		// vraca listu sa svim elementima osim onog koji brisemo
		$scope.building.institutions = $scope.building.institutions.filter(function(el) {
		    return el.id !== institution.id;
		});
		
		$http.put('api/buildings/' + $scope.building.id + '/' + institution.id).success(function(res) {
			$scope.building = null;
			$scope.buildingForm.$setPristine();
			$scope.deleteMessageInstitucija = "Institucija obrisana uspesno";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};

	var editBuilding = function(){
		$http.put('api/buildings/', $scope.building).success(function(res) {
			$scope.building = null;
			$scope.buildingForm.$setPristine();
			$scope.message = "Azuriranje uspesno";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};

	var addBuilding = function(){
		$http.post('api/buildings/', $scope.building).success(function(res) {
			$scope.building = null;
			$scope.buildingForm.$setPristine();
			$scope.message = "Zgrada kreirana";
			init();
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	$scope.submit = function() {
		if(edit){
			editBuilding();
		}else{
			addBuilding();	
		}
	};
	init();
});