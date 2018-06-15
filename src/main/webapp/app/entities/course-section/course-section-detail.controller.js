(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CourseSectionDetailController', CourseSectionDetailController);

    CourseSectionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CourseSection', 'Course'];

    function CourseSectionDetailController($scope, $rootScope, $stateParams, previousState, entity, CourseSection, Course) {
        var vm = this;

        vm.courseSection = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:courseSectionUpdate', function(event, result) {
            vm.courseSection = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
