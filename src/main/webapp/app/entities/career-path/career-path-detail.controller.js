(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CareerPathDetailController', CareerPathDetailController);

    CareerPathDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CareerPath'];

    function CareerPathDetailController($scope, $rootScope, $stateParams, previousState, entity, CareerPath) {
        var vm = this;

        vm.careerPath = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:careerPathUpdate', function(event, result) {
            vm.careerPath = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
