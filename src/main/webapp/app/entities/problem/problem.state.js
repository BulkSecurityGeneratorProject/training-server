(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('problem', {
            parent: 'entity',
            url: '/problem?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.problem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/problem/problems.html',
                    controller: 'ProblemController',
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
                    $translatePartialLoader.addPart('problem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('problem-detail', {
            parent: 'problem',
            url: '/problem/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.problem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/problem/problem-detail.html',
                    controller: 'ProblemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('problem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Problem', function($stateParams, Problem) {
                    return Problem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'problem',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('problem-detail.edit', {
            parent: 'problem-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/problem/problem-dialog.html',
                    controller: 'ProblemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Problem', function(Problem) {
                            return Problem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('problem.new', {
            parent: 'problem',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/problem/problem-dialog.html',
                    controller: 'ProblemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                problemUsername: null,
                                problemTitle: null,
                                problemContent: null,
                                reservedOne: null,
                                reservedTwo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('problem', null, { reload: 'problem' });
                }, function() {
                    $state.go('problem');
                });
            }]
        })
        .state('problem.edit', {
            parent: 'problem',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/problem/problem-dialog.html',
                    controller: 'ProblemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Problem', function(Problem) {
                            return Problem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('problem', null, { reload: 'problem' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('problem.delete', {
            parent: 'problem',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/problem/problem-delete-dialog.html',
                    controller: 'ProblemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Problem', function(Problem) {
                            return Problem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('problem', null, { reload: 'problem' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
