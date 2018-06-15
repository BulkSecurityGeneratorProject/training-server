(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('SectionContentDeleteController',SectionContentDeleteController);

    SectionContentDeleteController.$inject = ['$uibModalInstance', 'entity', 'SectionContent'];

    function SectionContentDeleteController($uibModalInstance, entity, SectionContent) {
        var vm = this;

        vm.sectionContent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SectionContent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
