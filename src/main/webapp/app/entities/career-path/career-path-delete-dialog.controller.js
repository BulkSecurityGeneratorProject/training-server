(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CareerPathDeleteController',CareerPathDeleteController);

    CareerPathDeleteController.$inject = ['$uibModalInstance', 'entity', 'CareerPath'];

    function CareerPathDeleteController($uibModalInstance, entity, CareerPath) {
        var vm = this;

        vm.careerPath = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CareerPath.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
