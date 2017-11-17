angular.module('app')

// Creating the Angular Controller

.controller('BuildingsController', function($http, $scope, AuthService) {

	var edit = false;
	
	$scope.buttonText = 'Create';
	
	var init = function() {
		$http.get('api/buildings').success(function(res) {
			$scope.buildings = res.buildings; // Buildings
			$scope.freeTenats = res.tenats; // Tenats without building
			$scope.buildingTenats = null; // Tenats in selected building
			$scope.buildingForm.$setPristine();
			$scope.message='';
			$scope.building = null;
			$scope.buttonText = 'Create';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	$scope.initEdit = function(building) {
		edit = true;
		$scope.building = building;
		$scope.buildingTenats = building.tenats;
		$scope.message='';
		$scope.buttonText = 'Update';
	};

	$scope.initAddBuilding = function() {
		edit = false;
		$scope.building = null;
		$scope.buildingForm.$setPristine();
		$scope.message='';
		$scope.buttonText = 'Create';
	};

	$scope.deleteBuilding = function(building) {
		$http.delete('api/buildings/'+building.id).success(function(res) {
			$scope.deleteMessage ="Success!";
			init();
		}).error(function(error) {
			$scope.deleteMessage = error.message;
		});
	};

	var editBuilding = function(){
		$http.put('api/buildings/', $scope.building).success(function(res) {
			$scope.building = null;
			$scope.buildingForm.$setPristine();
			$scope.message = "Editting Success";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};

	var addBuilding = function(){
		alert(JSON.stringify($scope.building));
		$http.post('api/buildings/', $scope.building).success(function(res) {
			$scope.building = null;
			$scope.buildingForm.$setPristine();
			$scope.message = "User Created";
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