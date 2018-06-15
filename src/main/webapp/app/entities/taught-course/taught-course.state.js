(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taught-course', {
            parent: 'entity',
            url: '/taught-course?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.taughtCourse.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taught-course/taught-courses.html',
                    controller: 'TaughtCourseController',
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
                    $translatePartialLoader.addPart('taughtCourse');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taught-course-detail', {
            parent: 'taught-course',
            url: '/taught-course/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.taughtCourse.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taught-course/taught-course-detail.html',
                    controller: 'TaughtCourseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taughtCourse');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TaughtCourse', function($stateParams, TaughtCourse) {
                    return TaughtCourse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'taught-course',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('taught-course-detail.edit', {
            parent: 'taught-course-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taught-course/taught-course-dialog.html',
                    controller: 'TaughtCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TaughtCourse', function(TaughtCourse) {
                            return TaughtCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taught-course.new', {
            parent: 'taught-course',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taught-course/taught-course-dialog.html',
                    controller: 'TaughtCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                active: null,
                                reservedOne: null,
                                reservedTwo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taught-course', null, { reload: 'taught-course' });
                }, function() {
                    $state.go('taught-course');
                });
            }]
        })
        .state('taught-course.edit', {
            parent: 'taught-course',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taught-course/taught-course-dialog.html',
                    controller: 'TaughtCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TaughtCourse', function(TaughtCourse) {
                            return TaughtCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taught-course', null, { reload: 'taught-course' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taught-course.delete', {
            parent: 'taught-course',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taught-course/taught-course-delete-dialog.html',
                    controller: 'TaughtCourseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TaughtCourse', function(TaughtCourse) {
                            return TaughtCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taught-course', null, { reload: 'taught-course' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
