(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('SectionContentDialogController', SectionContentDialogController);

    SectionContentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SectionContent', 'CourseSection'];

    function SectionContentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SectionContent, CourseSection) {
        var vm = this;

        vm.sectionContent = entity;
        vm.clear = clear;
        vm.save = save;
        vm.coursesections = CourseSection.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sectionContent.id !== null) {
                SectionContent.update(vm.sectionContent, onSaveSuccess, onSaveError);
            } else {
                SectionContent.save(vm.sectionContent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:sectionContentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
