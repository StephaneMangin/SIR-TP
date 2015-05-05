'use strict';

/**
 * @ngdoc overview
 * @name sirApp
 * @description
 * # sirApp
 *
 * Main module of the application.
 */
angular
  .module('sirApp', [
    'ng',
    'ngRoute',
    'ngResource',
    'ngDialog',
    'ngTable',
    'angular-growl',
    'ngAnimate',
    'ngCookies',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'app/views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'app/views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/homes', {
        templateUrl: 'app/views/homes.html',
        controller: 'HomeCtrl'
      })
      .when('/homes/:id', {
        templateUrl: 'app/views/home.html',
        controller: 'HomeCtrl'
      })
      .when('/persons', {
        templateUrl: 'app/views/persons.html',
        controller: 'PersonCtrl'
      })
      .when('/persons/:id', {
        templateUrl: 'app/views/person.html',
        controller: 'PersonCtrl'
      })
      .when('/heaters', {
        templateUrl: 'app/views/heaters.html',
        controller: 'HeaterCtrl'
      })
      .when('/heaters/:id', {
        templateUrl: 'app/views/heater.html',
        controller: 'HeaterCtrl'
      })
      .when('/devices', {
        templateUrl: 'app/views/devices.html',
        controller: 'DeviceCtrl'
      })
      .when('/devices/:id', {
        templateUrl: 'app/views/device.html',
        controller: 'DeviceCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
