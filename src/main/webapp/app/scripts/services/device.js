'use strict';

/**
 * @ngdoc service
 * @name sirApp.electronicDevice
 * @description
 * # electronicDevice
 * Factory in the sirApp.
 */
angular.module('sirApp')
  .factory('Device', ['$resource', function ($resource) {
    return $resource('rest/devices/:deviceId', {deviceId: '@id'}, {
        'save': {
            method: 'POST',
            params: {deviceId: '@other'}
        },
        'delete': {
            method: 'DELETE',
            params: {deviceId: "@id"}
        },
        'update': {
            method: 'PUT',
            params: {deviceId: "@other"}
        },
        'query': {
            method: 'GET',
            isArray: true,
            responseFormat: 'json'
        }
    });
  }]);
