(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('TaughtCourseDeleteController',TaughtCourseDeleteController);

    TaughtCourseDeleteController.$inject = ['$uibModalInstance', 'entity', 'TaughtCourse'];

    function TaughtCourseDeleteController($uibModalInstance, entity, TaughtCourse) {
        var vm = this;

        vm.taughtCourse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TaughtCourse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
