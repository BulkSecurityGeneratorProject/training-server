(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('SectionContent', SectionContent);

    SectionContent.$inject = ['$resource'];

    function SectionContent ($resource) {
        var resourceUrl =  'api/section-contents/:id';

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
