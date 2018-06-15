(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('ProblemDeleteController',ProblemDeleteController);

    ProblemDeleteController.$inject = ['$uibModalInstance', 'entity', 'Problem'];

    function ProblemDeleteController($uibModalInstance, entity, Problem) {
        var vm = this;

        vm.problem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Problem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
