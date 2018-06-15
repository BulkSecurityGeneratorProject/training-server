(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('TaughtCourseDialogController', TaughtCourseDialogController);

    TaughtCourseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TaughtCourse', 'Course', 'User'];

    function TaughtCourseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TaughtCourse, Course, User) {
        var vm = this;

        vm.taughtCourse = entity;
        vm.clear = clear;
        vm.save = save;
        vm.courses = Course.query({filter: 'taughtcourse-is-null'});
        $q.all([vm.taughtCourse.$promise, vm.courses.$promise]).then(function() {
            if (!vm.taughtCourse.courseId) {
                return $q.reject();
            }
            return Course.get({id : vm.taughtCourse.courseId}).$promise;
        }).then(function(course) {
            vm.courses.push(course);
        });
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.taughtCourse.id !== null) {
                TaughtCourse.update(vm.taughtCourse, onSaveSuccess, onSaveError);
            } else {
                TaughtCourse.save(vm.taughtCourse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:taughtCourseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
