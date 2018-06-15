(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('ProblemDetailController', ProblemDetailController);

    ProblemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Problem', 'User', 'Forum'];

    function ProblemDetailController($scope, $rootScope, $stateParams, previousState, entity, Problem, User, Forum) {
        var vm = this;

        vm.problem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:problemUpdate', function(event, result) {
            vm.problem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
