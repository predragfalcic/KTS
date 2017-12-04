angular.module('app')

// Creating the Angular Controller

.controller('BuildingsController', function($http, $scope, AuthService) {

	var edit = false;
	
	$scope.buttonText = 'Dodaj';
	
	var init = function() {
		$http.get('api/buildings').success(function(res) {
			$scope.buildings = res.buildings; // Buildings
			$scope.apartmens = res.apartmens; // apartmens without building
			$scope.buildingForm.$setPristine();
			$scope.message='';
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
		$scope.deleteMessageStan = '';
		$scope.buttonText = 'Azuriraj';
	};

	$scope.initAddBuilding = function() {
		edit = false;
		$scope.building = null;
		$scope.buildingForm.$setPristine();
		$scope.message='';
		$scope.deleteMessageStan = '';
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
			$scope.buildingForm.$setPristine();
			$scope.deleteMessageInstitucija = "Institucija obrisana uspesno";
			$scope.institutions = $scope.building.institutions;
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};
	
	// Brisemo stan iz zgrade
	$scope.deleteStanFromBuilding = function(stan) {
		// vraca listu sa svim elementima osim onog koji brisemo
//		$scope.apartmen.apartmen_tenats = $scope.apartmen.apartmen_tenats.filter(function(el) {
//		    return el.id !== stanar.id;
//		});
//		
		$http.put('api/buildings/delete/stan/' + $scope.building.id + '/' + stan.id).success(function(res) {
			$scope.building = res.building;
			$scope.buildingForm.$setPristine();
			$scope.deleteMessageStan = "Stan uspesno obrisan iz zgrade";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};
	
	// Dodajemo stan u zgradu
	$scope.addStanToBuilding = function(stan) {
		$http.put('api/buildings/add/stan/' + $scope.building.id + '/' + stan.id).success(function(res) {
			$scope.building = res.building;
			$scope.buildingForm.$setPristine();
			$scope.deleteMessageStan = "Stan uspesno dodat u zgradu";
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