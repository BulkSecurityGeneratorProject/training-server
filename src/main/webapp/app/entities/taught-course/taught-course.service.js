(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('TaughtCourse', TaughtCourse);

    TaughtCourse.$inject = ['$resource'];

    function TaughtCourse ($resource) {
        var resourceUrl =  'api/taught-courses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
