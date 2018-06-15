(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('OrdersDialogController', OrdersDialogController);

    OrdersDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orders', 'Course', 'User'];

    function OrdersDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Orders, Course, User) {
        var vm = this;

        vm.orders = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.courses = Course.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orders.id !== null) {
                Orders.update(vm.orders, onSaveSuccess, onSaveError);
            } else {
                Orders.save(vm.orders, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:ordersUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateOfPayment = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
