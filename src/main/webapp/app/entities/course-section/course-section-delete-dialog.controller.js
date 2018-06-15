(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CourseSectionDeleteController',CourseSectionDeleteController);

    CourseSectionDeleteController.$inject = ['$uibModalInstance', 'entity', 'CourseSection'];

    function CourseSectionDeleteController($uibModalInstance, entity, CourseSection) {
        var vm = this;

        vm.courseSection = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CourseSection.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
