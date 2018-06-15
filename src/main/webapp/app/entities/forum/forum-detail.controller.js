(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('ForumDetailController', ForumDetailController);

    ForumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Forum', 'Course'];

    function ForumDetailController($scope, $rootScope, $stateParams, previousState, entity, Forum, Course) {
        var vm = this;

        vm.forum = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:forumUpdate', function(event, result) {
            vm.forum = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
