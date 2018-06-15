(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('TaughtCourseDetailController', TaughtCourseDetailController);

    TaughtCourseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TaughtCourse', 'Course', 'User'];

    function TaughtCourseDetailController($scope, $rootScope, $stateParams, previousState, entity, TaughtCourse, Course, User) {
        var vm = this;

        vm.taughtCourse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:taughtCourseUpdate', function(event, result) {
            vm.taughtCourse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
