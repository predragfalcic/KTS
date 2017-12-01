angular.module('app')

// Creating the Angular Controller

.controller('ApartmensController', function($http, $scope, AuthService) {

	var edit = false;
	
	$scope.buttonText = 'Dodaj';
	$scope.stanari = [];
	$scope.owner = null; // Kasnije ce biti ovde smesten korisnik koji je vlasnik stana
	
	var init = function() {
		$http.get('api/apartmens').success(function(res) {
			$scope.apartmens = res.apartmens; // apartmens
			$scope.freeTenats = res.tenats; // Tenats without apartmen
			$scope.apartmenForm.$setPristine();
			$scope.message='';
			$scope.buttonText = 'Dodaj';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};
	        
	$scope.initEdit = function(apartmen) {
		edit = true;
		$scope.apartmen = null;
		$scope.apartmen = apartmen;
		$scope.message='';
		$scope.deleteMessageStanar = '';
		$scope.buttonText = 'Azuriraj';
	};

	$scope.initAddApartmen = function() {
		edit = false;
		$scope.apartmen = null;
		$scope.apartmenForm.$setPristine();
		$scope.message='';
		$scope.deleteMessageStanar = '';
		$scope.buttonText = 'Dodaj';
	};

	$scope.deleteApartmen = function(apartmen) {
		$http.delete('api/apartmens/'+apartmen.id).success(function(res) {
			$scope.deleteMessage ="Uspesno!";
			init();
		}).error(function(error) {
			$scope.deleteMessage = error.message;
		});
	};

	var editapartmen = function(){
		$http.put('api/apartmens/', $scope.apartmen).success(function(res) {
			$scope.apartmen = null;
			$scope.apartmenForm.$setPristine();
			$scope.message = "Azuriranje uspesno";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};

	// Return the whole object that is owner
	function findOwner(tenat) { 
	    return tenat.owner === true;
	}
	
	var addapartmen = function(){
		$http.post('api/apartmens/', $scope.apartmen).success(function(res) {
			$scope.apartmen = null;
			$scope.apartmenForm.$setPristine();
			$scope.message = "Zgrada kreirana";
			init();
		}).error(function(error) {
			$scope.message = error.message;
		});
	};
	
	// Brisemo stanara iz apartmana
	$scope.deleteStanarFromApartman = function(stanar) {
		// vraca listu sa svim elementima osim onog koji brisemo
//		$scope.apartmen.apartmen_tenats = $scope.apartmen.apartmen_tenats.filter(function(el) {
//		    return el.id !== stanar.id;
//		});
//		
		$http.put('api/apartmens/delete/tenat/' + $scope.apartmen.id + '/' + stanar.id).success(function(res) {
			$scope.apartmen = res.apartmen;
			$scope.apartmenForm.$setPristine();
			$scope.deleteMessageStanar = "Stanar uspesno obrisan iz stana";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};
	
	// Dodajemo stanara u apartman
	$scope.addStanarToApartman = function(stanar) {
		$http.put('api/apartmens/add/tenat/' + $scope.apartmen.id + '/' + stanar.id).success(function(res) {
			$scope.apartmen = res.apartmen;
			$scope.apartmenForm.$setPristine();
			$scope.deleteMessageStanar = "Stanar uspesno dodat u stan";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};
	
	// Biramo vlasnika stana
	$scope.setOwnerForApartman = function(stanar) {
		$http.put('api/apartmens/add/owner/' + $scope.apartmen.id + '/' + stanar.id).success(function(res) {
			$scope.apartmen = res.apartmen;
			$scope.apartmenForm.$setPristine();
			$scope.deleteMessageStanar = "Stanar uspesno postavljen kao vlasnik stana.";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};
	
	// brisemo vlasnika stana
	$scope.deleteOwnerForApartman = function(stanar) {
		$http.put('api/apartmens/delete/owner/' + $scope.apartmen.id + '/' + stanar.id).success(function(res) {
			$scope.apartmen = res.apartmen;
			$scope.apartmenForm.$setPristine();
			$scope.deleteMessageStanar = "Stanar obrisan kao vlasnik stana.";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};

	$scope.submit = function() {
		if(edit){
			editapartmen();
		}else{
			addapartmen();	
		}
	};
	init();
});