(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('ProblemDialogController', ProblemDialogController);

    ProblemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Problem', 'User', 'Forum'];

    function ProblemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Problem, User, Forum) {
        var vm = this;

        vm.problem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.forums = Forum.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.problem.id !== null) {
                Problem.update(vm.problem, onSaveSuccess, onSaveError);
            } else {
                Problem.save(vm.problem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:problemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
