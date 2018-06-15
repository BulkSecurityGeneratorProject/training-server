(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('Orders', Orders);

    Orders.$inject = ['$resource', 'DateUtils'];

    function Orders ($resource, DateUtils) {
        var resourceUrl =  'api/orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateOfPayment = DateUtils.convertDateTimeFromServer(data.dateOfPayment);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
