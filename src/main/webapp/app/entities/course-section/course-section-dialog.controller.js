(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CourseSectionDialogController', CourseSectionDialogController);

    CourseSectionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CourseSection', 'Course'];

    function CourseSectionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CourseSection, Course) {
        var vm = this;

        vm.courseSection = entity;
        vm.clear = clear;
        vm.save = save;
        vm.courses = Course.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.courseSection.id !== null) {
                CourseSection.update(vm.courseSection, onSaveSuccess, onSaveError);
            } else {
                CourseSection.save(vm.courseSection, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:courseSectionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
