(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('OrdersDetailController', OrdersDetailController);

    OrdersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orders', 'Course', 'User'];

    function OrdersDetailController($scope, $rootScope, $stateParams, previousState, entity, Orders, Course, User) {
        var vm = this;

        vm.orders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:ordersUpdate', function(event, result) {
            vm.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
