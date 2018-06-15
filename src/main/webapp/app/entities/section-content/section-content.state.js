(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('section-content', {
            parent: 'entity',
            url: '/section-content?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.sectionContent.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/section-content/section-contents.html',
                    controller: 'SectionContentController',
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
                    $translatePartialLoader.addPart('sectionContent');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('section-content-detail', {
            parent: 'section-content',
            url: '/section-content/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.sectionContent.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/section-content/section-content-detail.html',
                    controller: 'SectionContentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sectionContent');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SectionContent', function($stateParams, SectionContent) {
                    return SectionContent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'section-content',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('section-content-detail.edit', {
            parent: 'section-content-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/section-content/section-content-dialog.html',
                    controller: 'SectionContentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SectionContent', function(SectionContent) {
                            return SectionContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('section-content.new', {
            parent: 'section-content',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/section-content/section-content-dialog.html',
                    controller: 'SectionContentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sectionTime: null,
                                sectionTitle: null,
                                sectionContent: null,
                                reservedOne: null,
                                reservedTwo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('section-content', null, { reload: 'section-content' });
                }, function() {
                    $state.go('section-content');
                });
            }]
        })
        .state('section-content.edit', {
            parent: 'section-content',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/section-content/section-content-dialog.html',
                    controller: 'SectionContentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SectionContent', function(SectionContent) {
                            return SectionContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('section-content', null, { reload: 'section-content' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('section-content.delete', {
            parent: 'section-content',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/section-content/section-content-delete-dialog.html',
                    controller: 'SectionContentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SectionContent', function(SectionContent) {
                            return SectionContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('section-content', null, { reload: 'section-content' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
