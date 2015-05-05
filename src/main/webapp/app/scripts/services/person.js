'use strict';

/**
 * @ngdoc service
 * @name sirApp.person
 * @description
 * # person
 * Factory in the sirApp.
 */
angular.module('sirApp')
  .factory('Person', ['$resource', function ($resource) {
    return $resource('rest/persons/:personId', {personId: '@id'}, {
        'save': {
            method: 'POST',
            params: {personId: '@other'}
        },
        'delete': {
            method: 'DELETE',
            params: {personId: "@id"}
        },
        'update': {
            method: 'PUT',
            params: {personId: '@other'}
        },
        'query': {
            method: 'GET',
            isArray: true,
            responseFormat: 'json'
        }
    });
  }]);
