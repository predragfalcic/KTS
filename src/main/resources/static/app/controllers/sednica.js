/**
 * @Author Predrag Falcic
 */

angular.module('app')

// Creating the Angular Controller

.controller('SednicaController', function($http, $scope, AuthService) {
	$scope.addSednicaMessage = '';
	$scope.dateString = new Date();
	$scope.sednica = null;
	$scope.sednice = null;
	$scope.zap = null;
	$scope.stavka = null;
	
	$scope.tenat = AuthService.user;
	
	var init = function() {
		$http.get('api/sednica/' + $scope.tenat.id).success(function(res) {
			$scope.addSednicaMessage = '';
			$scope.stavkaMessage = '';
			$scope.sednica = null;
			$scope.sednice = res;
			$scope.message = '';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	init();
	
	$scope.addSednica = function(){
		var sednicaDTO = {'sednica': $scope.sednica,
						  'dateScheduled': $scope.dateString.getTime(),
						  'creator': $scope.tenat};
		$http.post('api/sednica/', sednicaDTO).success(function(res){
			$scope.sednica = null;
			$scope.sednice = res;
			$scope.addSednicaMessage = 'Uspesno.';
			$scope.stavkaMessage = '';
			$scope.zapisnikMessage = '';
			$scope.message = '';
		}).error(function(error){
			$scope.message = error.message;
		});
	};
	
	$scope.sednicaDetails = function(s){
		$scope.sednica = s;
		$http.get('api/sednica_details/' + s.id + '/' + AuthService.user.id).success(function(res){
			$scope.tenat = res.tenat;
			$scope.stavke = res.stavke;
			$scope.zapisnik = res.zapisnici[0];
			$scope.addSednicaMessage = '';
			$scope.stavkaMessage = '';
			$scope.zapisnikMessage = '';
			$scope.message = '';
		}).error(function(error){
			$scope.message = error.message;
		});
	};
	
	$scope.addStavka = function(){
		if($scope.sednica == null){
			$scope.message = "Prvo izaberite sednicu.";
		}else{
			var stavkaDTO = {'stavka': $scope.stavka, 'sednicaId': $scope.sednica.id, 'tenatId': AuthService.user.id};
			$http.post('api/sednica/stavka/', stavkaDTO).success(function(res){
				$scope.stavke = res.stavke;
				$scope.stavkaMessage = 'Stavka uspesno dodata.';
				$scope.addSednicaMessage = '';
				$scope.message = '';
				$scope.zapisnikMessage = '';
				$scope.stavka = null;
			}).error(function(error){
				$scope.message = error.message;
			});
		}
	};
	$scope.addZapisnik = function(){
		if($scope.sednica == null){
			$scope.message = "Prvo izaberite sednicu.";
		}else{
			var zapisnikDTO = {'zapisnik': $scope.zap, 'sednicaId': $scope.sednica.id, 'tenatId': AuthService.user.id};
			$http.post('api/sednica/zapisnik/', zapisnikDTO).success(function(res){
				$scope.zapisnik = res.zapisnici[0];
				$scope.zapisnikMessage = 'Zapisnik uspesno dodat.';
				$scope.addSednicaMessage = '';
				$scope.message = '';
				$scope.stavkaMessage = '';
				$scope.sednica = res.sednica;
				$scope.zap = null;
			}).error(function(error){
				$scope.message = error.message;
			});
		}
	};
});