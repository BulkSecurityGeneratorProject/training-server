(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('LanguageDetailController', LanguageDetailController);

    LanguageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Language', 'Forum', 'Course'];

    function LanguageDetailController($scope, $rootScope, $stateParams, previousState, entity, Language, Forum, Course) {
        var vm = this;

        vm.language = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:languageUpdate', function(event, result) {
            vm.language = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
