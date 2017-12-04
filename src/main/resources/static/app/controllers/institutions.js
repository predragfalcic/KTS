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
			$scope.freeWorkers = res.workers; // Workers without institution
			$scope.institutionBuildings = []; // institution's buildings
			$scope.institutionForm.$setPristine();
			$scope.message='';
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
		$scope.deleteMessageWorker = '';
		$scope.buttonText = 'Azuriraj';
	};

	$scope.initAddInstitution = function() {
		edit = false;
		$scope.institution = null;
		$scope.institutionForm.$setPristine();
		$scope.message='';
		$scope.deleteMessageWorker = '';
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
	
	// Brisemo radnika iz institucije
	$scope.deleteWorkerFromInstitution = function(worker) {

		$http.put('api/institutions/delete/worker/' + $scope.institution.id + '/' + worker.id).success(function(res) {
			$scope.institution = res.institution;
			$scope.freeWorkers = res.workers; // Workers without institution
			$scope.apartmenForm.$setPristine();
			$scope.deleteMessageWorker = "Radnik uspesno obrisan iz institucije";
			init();
		}).error(function(error) {
			$scope.message =error.message;
		});
	};
	
	// Dodajemo workera u Institution
	$scope.addWorkerToInstitution = function(worker) {
		$http.put('api/institutions/add/worker/' + $scope.institution.id + '/' + worker.id).success(function(res) {
			$scope.institution = res.institution;
			$scope.institutionForm.$setPristine();
			$scope.deleteMessageWorker = "Radnik uspesno dodat u instituciju";
			init();
		}).error(function(error) {
			$scope.message =error.message;
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