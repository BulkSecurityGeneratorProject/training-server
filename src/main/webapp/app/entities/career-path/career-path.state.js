(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('career-path', {
            parent: 'entity',
            url: '/career-path?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.careerPath.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-path/career-paths.html',
                    controller: 'CareerPathController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('careerPath');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('career-path-detail', {
            parent: 'career-path',
            url: '/career-path/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.careerPath.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-path/career-path-detail.html',
                    controller: 'CareerPathDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('careerPath');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CareerPath', function($stateParams, CareerPath) {
                    return CareerPath.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'career-path',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('career-path-detail.edit', {
            parent: 'career-path-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-path/career-path-dialog.html',
                    controller: 'CareerPathDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPath', function(CareerPath) {
                            return CareerPath.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-path.new', {
            parent: 'career-path',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-path/career-path-dialog.html',
                    controller: 'CareerPathDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                careerPathName: null,
                                careerPathIntroduce: null,
                                careerPathImg: null,
                                reservedOne: null,
                                reservedTwo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('career-path', null, { reload: 'career-path' });
                }, function() {
                    $state.go('career-path');
                });
            }]
        })
        .state('career-path.edit', {
            parent: 'career-path',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-path/career-path-dialog.html',
                    controller: 'CareerPathDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPath', function(CareerPath) {
                            return CareerPath.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-path', null, { reload: 'career-path' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-path.delete', {
            parent: 'career-path',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-path/career-path-delete-dialog.html',
                    controller: 'CareerPathDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CareerPath', function(CareerPath) {
                            return CareerPath.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-path', null, { reload: 'career-path' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
