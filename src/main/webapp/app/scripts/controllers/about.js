'use strict';

/**
 * @ngdoc function
 * @name sirApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the sirApp
 */
angular.module('sirApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
