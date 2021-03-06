angular.module('app').config(function($stateProvider, $urlRouterProvider) {
	
	// the ui router will redirect if a invalid state has come.
	$urlRouterProvider.otherwise('/page-not-found');
	// parent view - navigation state
	$stateProvider.state('nav', {
		abstract : true,
		url : '',
		views : {
			'nav@' : {
				templateUrl : 'app/views/nav.html',
				controller : 'NavController'
			}
		}
	}).state('login', {
		parent : 'nav',
		url : '/login',
		views : {
			'content@' : {
				templateUrl : 'app/views/login.html',
				controller : 'LoginController'
			}
		}
	}).state('sednica', {
		parent : 'nav',
		url : '/sednica',
		views : {
			'content@' : {
				templateUrl : 'app/views/sednica.html',
				controller : 'SednicaController'
			}
		}
	}).state('users', {
		parent : 'nav',
		url : '/users',
		data : {
			role : 'ADMIN'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/users.html',
				controller : 'UsersController',
			}
		}
	}).state('buildings', {
		parent : 'nav',
		url : '/buildings',
		data : {
			role : 'ADMIN'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/buildings.html',
				controller : 'BuildingsController',
			}
		}
	}).state('profil', {
		parent : 'nav',
		url : '/profil',
		data : {
			role : 'STANAR'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/tenat.html',
				controller : 'TenatController',
			}
		}
	})
	.state('worker', {
		parent : 'nav',
		url : '/worker',
		data : {
			role : 'WORKER'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/worker.html',
				controller : 'WorkerController',
			}
		}
	}).state('apartmens', {
		parent : 'nav',
		url : '/apartmens',
		data : {
			role : 'ADMIN'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/apartmens.html',
				controller : 'ApartmensController',
			}
		}
	}).state('institutions', {
		parent : 'nav',
		url : '/institutions',
		data : {
			role : 'ADMIN'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/institutions.html',
				controller : 'InstitutionsController',
			}
		}
	}).state('tenat_institutions', {
		parent : 'nav',
		url : '/tenat_institutions',
		data : {
			role : 'STANAR'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/tenat_institutions.html',
				controller : 'TenatInstitutionsController',
			}
		}
	}).state('home', {
		parent : 'nav',
		url : '/',
		views : {
			'content@' : {
				templateUrl : 'app/views/home.html',
				controller : 'HomeController'
			}
		}
	}).state('page-not-found', {
		parent : 'nav',
		url : '/page-not-found',
		views : {
			'content@' : {
				templateUrl : 'app/views/page-not-found.html',
				controller : 'PageNotFoundController'
			}
		}
	}).state('access-denied', {
		parent : 'nav',
		url : '/access-denied',
		views : {
			'content@' : {
				templateUrl : 'app/views/access-denied.html',
				controller : 'AccessDeniedController'
			}
		}
	}).state('register', {
		parent : 'nav',
		url : '/register',
		views : {
			'content@' : {
				templateUrl : 'app/views/register.html',
				controller : 'RegisterController'
			}
		}
	});
});