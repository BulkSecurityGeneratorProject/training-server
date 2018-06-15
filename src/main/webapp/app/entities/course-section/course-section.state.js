(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('course-section', {
            parent: 'entity',
            url: '/course-section?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.courseSection.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-section/course-sections.html',
                    controller: 'CourseSectionController',
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
                    $translatePartialLoader.addPart('courseSection');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('course-section-detail', {
            parent: 'course-section',
            url: '/course-section/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.courseSection.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-section/course-section-detail.html',
                    controller: 'CourseSectionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('courseSection');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CourseSection', function($stateParams, CourseSection) {
                    return CourseSection.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'course-section',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('course-section-detail.edit', {
            parent: 'course-section-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-section/course-section-dialog.html',
                    controller: 'CourseSectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseSection', function(CourseSection) {
                            return CourseSection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-section.new', {
            parent: 'course-section',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-section/course-section-dialog.html',
                    controller: 'CourseSectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                courseSectionTitle: null,
                                reservedOne: null,
                                reservedTwo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('course-section', null, { reload: 'course-section' });
                }, function() {
                    $state.go('course-section');
                });
            }]
        })
        .state('course-section.edit', {
            parent: 'course-section',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-section/course-section-dialog.html',
                    controller: 'CourseSectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseSection', function(CourseSection) {
                            return CourseSection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-section', null, { reload: 'course-section' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-section.delete', {
            parent: 'course-section',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-section/course-section-delete-dialog.html',
                    controller: 'CourseSectionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CourseSection', function(CourseSection) {
                            return CourseSection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-section', null, { reload: 'course-section' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
