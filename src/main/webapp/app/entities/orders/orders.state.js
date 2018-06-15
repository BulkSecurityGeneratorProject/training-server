(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('orders', {
            parent: 'entity',
            url: '/orders?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.orders.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orders/orders.html',
                    controller: 'OrdersController',
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
                    $translatePartialLoader.addPart('orders');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('orders-detail', {
            parent: 'orders',
            url: '/orders/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.orders.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orders/orders-detail.html',
                    controller: 'OrdersDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orders');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Orders', function($stateParams, Orders) {
                    return Orders.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'orders',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('orders-detail.edit', {
            parent: 'orders-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orders/orders-dialog.html',
                    controller: 'OrdersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orders', function(Orders) {
                            return Orders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orders.new', {
            parent: 'orders',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orders/orders-dialog.html',
                    controller: 'OrdersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderNumber: null,
                                dateOfPayment: null,
                                payTheAmount: null,
                                theDrawee: null,
                                payForCourses: null,
                                reservedOne: null,
                                reservedTwo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('orders', null, { reload: 'orders' });
                }, function() {
                    $state.go('orders');
                });
            }]
        })
        .state('orders.edit', {
            parent: 'orders',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orders/orders-dialog.html',
                    controller: 'OrdersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orders', function(Orders) {
                            return Orders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orders', null, { reload: 'orders' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orders.delete', {
            parent: 'orders',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orders/orders-delete-dialog.html',
                    controller: 'OrdersDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Orders', function(Orders) {
                            return Orders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orders', null, { reload: 'orders' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
