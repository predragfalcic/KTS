angular.module('app')

// Creating the Angular Controller

.controller('TenatController', function($http, $scope, AuthService) {
	$scope.allOwners = null;
	
	var init = function() {
		$http.get('api/profil/' + AuthService.user.id).success(function(res) {
			$scope.tenat = res.tenat; // Stanar
			$scope.building = res.building; // Zgrada stanara
			$scope.apartment = res.apartmen; // Stan stanara
			needToVode();
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	init();
	
	// Ukoliko zgrada nema predsednika skupstine stanara
	// onda vlasnicima stanova treba omoguciti da glasaju za nekog korisnika
	var needToVode = function(){
		if($scope.building.hasPresident === false){
			$http.get('api/profil/owners/' + AuthService.user.id).success(function(res) {
				$scope.allOwners = res;
			}).error(function(error) {
				$scope.message = error.message;
			});
		}else{
			$scope.allOwners = null;
		}
	};
	
	// Glasamo za nekog od vlasnika stana da bude predsednik
	// Saljemo id korisnika za koga smo glasali
	$scope.voteForPresident = function(predsednik) {
		$http.put('api/profil/president/' + predsednik.id + '/' + $scope.tenat.id).success(function(res) {
			$scope.tenat = res.tenat; // Stanar
			$scope.building = res.building; // Zgrada stanara
			$scope.apartment = res.apartmen; // Stan stanara
			$scope.addMessage ="Uspesno!";
			needToVode();
		}).error(function(error) {
			$scope.addMessage = error.message;
		});
	};
});