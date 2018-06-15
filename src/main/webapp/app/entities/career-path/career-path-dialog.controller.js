(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CareerPathDialogController', CareerPathDialogController);

    CareerPathDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CareerPath'];

    function CareerPathDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CareerPath) {
        var vm = this;

        vm.careerPath = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.careerPath.id !== null) {
                CareerPath.update(vm.careerPath, onSaveSuccess, onSaveError);
            } else {
                CareerPath.save(vm.careerPath, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:careerPathUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
