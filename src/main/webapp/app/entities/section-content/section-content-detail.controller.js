(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('SectionContentDetailController', SectionContentDetailController);

    SectionContentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SectionContent', 'CourseSection'];

    function SectionContentDetailController($scope, $rootScope, $stateParams, previousState, entity, SectionContent, CourseSection) {
        var vm = this;

        vm.sectionContent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:sectionContentUpdate', function(event, result) {
            vm.sectionContent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
