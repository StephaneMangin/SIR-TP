'use strict';

/**
 * @ngdoc service
 * @name sirApp.home
 * @description
 * # home
 * Factory in the sirApp.
 */
angular.module('sirApp')
  .factory('Home', ['$resource', function ($resource) {
    return $resource('rest/homes/:homeId', {homeId: '@id'}, {
        'save': {
            method: 'POST',
            params: {homeId: '@other'}
        },
        'delete': {
            method: 'DELETE',
            params: {homeId: "@id"}
        },
        'update': {
            method: 'PUT',
            params: {homeId: "@other"}
        },
        'query': {
            method: 'GET',
            isArray: true,
            responseFormat: 'json'
        }
    });
  }]);
