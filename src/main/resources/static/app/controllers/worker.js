angular.module('app')

// Creating the Angular Controller

.controller('WorkerController', function($http, $scope, AuthService) {
	//$scope.buildings = null;
	$scope.building = null;
	$scope.failure = null;
	$scope.failures = null;
	$scope.prihvatiMessage = '';
	$scope.dateString = new Date();
	$scope.comments = null;
	$scope.comment = null;
	
	var init = function() {
		$http.get('api/worker/' + AuthService.user.id).success(function(res) {
			$scope.worker = res.worker; // Radnik
			$scope.institution = res.institution; // Institucija radnika
			$scope.workerFailures = res.workerFailures; // Kvarovi za koje je ovaj radnik zaduzen
			$scope.prihvatiMessage = '';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};

	init();
	
	// Vracamo sve kvarove koje izabrana zgrada ima
	$scope.buildingDetails = function(b){
		$scope.building = null;
		$scope.building = b;
		$scope.prihvatiMessage = '';
		
		$http.get('api/worker/failures/' + $scope.building.id).success(function(res) {
			$scope.failures = res.failures;
			$scope.prihvatiMessage = '';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};
	
	// Prikazujemo detalje kvara
	$scope.failureDetails = function(f){
		$scope.failure = null;
		$scope.failure = f;
		$http.get('api/komentar/' + $scope.failure.id).success(function(res) {
			$scope.comments = res;
			$scope.prihvatiMessage = '';
		}).error(function(error) {
			$scope.message = error.message;
		});
	};
	
	// Radnik prihvata kvar
	$scope.prihvati = function(f){
		$http.put('api/worker/' + AuthService.user.id + '/' + f.id).success(function(res){
			$scope.worker = res.worker;
			$scope.institution = res.institution;
			$scope.failures = res.failures;
			$scope.workerFailures = res.workerFailures; // Kvarovi za koje je ovaj radnik zaduzen
			$scope.failure = null;
			$scope.prihvatiMessage = 'Uspesno ste prihvatili kvar';
		}).error(function(error){
			$scope.message = error.message;
		});
	};
	
	// Radnik otkazuje kvar
	$scope.otkazi = function(f){
		$http.put('api/worker/decline/' + AuthService.user.id + '/' + f.id).success(function(res){
			$scope.worker = res.worker;
			$scope.institution = res.institution;
			$scope.failures = res.failures;
			$scope.workerFailures = res.workerFailures; // Kvarovi za koje je ovaj radnik zaduzen
			$scope.failure = null;
			$scope.prihvatiMessage = 'Vise niste zaduzeni za kvar.';
		}).error(function(error){
			$scope.message = error.message;
		});
	};
	
	// Radnik zakazuje termin popravke kvara
	$scope.zakaziPopravku = function(f){
		var failureDate = {'failure': f, 'date': $scope.dateString.getTime()};
		$http.put('api/worker/zakazi/', failureDate).success(function(res){
			$scope.worker = res.worker;
			$scope.institution = res.institution;
			$scope.failures = res.failures;
			$scope.workerFailures = res.workerFailures; // Kvarovi za koje je ovaj radnik zaduzen
			$scope.failure = null;
			$scope.prihvatiMessage = 'Termin je zakazan.';
		}).error(function(error){
			$scope.message = error.message;
		});
	};
	
	// Radnik zakazuje termin popravke kvara
	$scope.otkaziPopravku = function(f){
		$http.put('api/worker/otkazi/', f).success(function(res){
			$scope.worker = res.worker;
			$scope.institution = res.institution;
			$scope.failures = res.failures;
			$scope.workerFailures = res.workerFailures; // Kvarovi za koje je ovaj radnik zaduzen
			$scope.failure = null;
			$scope.prihvatiMessage = 'Termin je otkazan.';
		}).error(function(error){
			$scope.message = error.message;
		});
	};
	
	// Radnik zakazuje termin popravke kvara
	$scope.zavrsenPosao = function(f){
		$http.put('api/worker/zavrsen/', f).success(function(res){
			$scope.worker = res.worker;
			$scope.institution = res.institution;
			$scope.failures = res.failures;
			$scope.workerFailures = res.workerFailures; // Kvarovi za koje je ovaj radnik zaduzen
			$scope.failure = null;
			$scope.prihvatiMessage = 'Kvar je popravljen.';
		}).error(function(error){
			$scope.message = error.message;
		});
	};
	
	// Kreiranje komentara na kvar
	$scope.addComment = function(c){
		var objectDTO = {'comment': c, 'failure': $scope.failure, 'author': AuthService.user}
		$http.post('api/komentar/', objectDTO).success(function(res){
			$scope.comments = res;
			$scope.comment = null;
		}).error(function(error){
			$scope.message = error.message;
		});
	};
});



















