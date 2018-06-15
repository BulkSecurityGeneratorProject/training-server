(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('CareerPath', CareerPath);

    CareerPath.$inject = ['$resource'];

    function CareerPath ($resource) {
        var resourceUrl =  'api/career-paths/:id';

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
