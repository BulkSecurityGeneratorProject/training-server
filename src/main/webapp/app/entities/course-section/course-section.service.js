(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('CourseSection', CourseSection);

    CourseSection.$inject = ['$resource'];

    function CourseSection ($resource) {
        var resourceUrl =  'api/course-sections/:id';

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
