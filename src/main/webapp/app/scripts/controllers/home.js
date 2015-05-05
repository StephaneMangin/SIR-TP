(function () {

  'use strict';

  /**
   * @ngdoc function
   * @name sirApp.controller:HomeCtrl
   * @description
   * # HomeCtrl
   * Controller of the sirApp
   */
  angular
    .module('sirApp')
    .controller('HomeCtrl', function ($rootScope, $route, $scope, $routeParams, $http, $location, Home, ngDialog, $filter, ngTableParams, growl) {

      $scope.getHome = function() {
        $scope.home = Home.get({homeId: $routeParams.id}, function (response) {

          $scope.tableHomeHeaters = new ngTableParams({
                page: 1,            // show first page
                count: 10          // count per page
            }, {
                counts: [], // hide page counts control
                total: 1,  // value less than count hide pagination
                getData: function ($defer, params) {
                    var devices = $filter('filter')($scope.home.devices, { type: 'Heater'});
                    params.total(devices.length);
                    $defer.resolve(devices);
                }
            });

          $scope.tableHomeDevices = new ngTableParams({
                page: 1,            // show first page
                count: 10          // count per page
            }, {
                counts: [], // hide page counts control
                total: 1,  // value less than count hide pagination
                getData: function ($defer, params) {
                    var devices = $filter('filter')($scope.home.devices, { type: 'ElectronicDevice'});
                    params.total(devices.length);
                    $defer.resolve(devices);
                }
            });
         });
      };

      $scope.getHomes = function () {
          Home.query(function (response) {
            $scope.homes = response;
              $scope.tableParams = new ngTableParams({
                  page: 1,            // show first page
                  count: 10          // count per page
              }, {
                  total: $scope.homes.length, // length of data
                  getData: function ($defer, params) {
                      params.total($scope.homes.length);
                      $defer.resolve($scope.homes);
                  }
              });
          });
      };

      $scope.delete = function (id) {
        Home.get({homeId: id}, function (response) {
          response.$delete({homeId: response.id}, function () {
            growl.success("Home deleted.");
                $location.path('/homes');
            });
        });
      };

      $scope.deleteDevice = function (home, device) {
        for (var dev in home.devices) {
            if (dev.id == device.id) {
              delete home.devices[dev];
            }
        }
        home.$update({}, function () {});
      };

      $scope.addHome = function () {
          ngDialog.open({
              template: 'app/views/homeAdd.html',
              controller: 'HomeCreateCtrl',
              appendTo: '#dialog-home',
              scope: $scope
          });
      };

      $scope.updateHome = function () {
          ngDialog.open({
              template: 'app/views/homeAdd.html',
              controller: 'HomeUpdateCtrl',
              appendTo: '#dialog-home',
              scope: $scope
          });
      };
  })
  .controller('HomeCreateCtrl', function ($rootScope, $scope, $routeParams, $http, $location, Home, Person, ngDialog, growl) {
      $scope.titleNgDialog = "Add a home";

		$scope.persons = Person.query();

      $scope.dateOptions = {
          startingDay: 1
        };

      $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
      };
      $scope.home = new Home();

      $scope.submit = function () {

          $scope.home.$save(
              function (data) {
                  growl.success("Home has been created.");
              },
              function (error) {
                  console.log(error);
                  growl.error("An error occurs while creating the home !");
              }
          );
          $scope.closeThisDialog();
      };
  })
  .controller('HomeUpdateCtrl', function ($rootScope, $scope, $routeParams, $http, $location, Home, ngDialog, growl) {
      $scope.titleNgDialog = "Update a home";
      $scope.update = true;

      $scope.dateOptions = {
          startingDay: 1
        };

      $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
      };
      $scope.submit = function () {
          delete $scope.home.devices;
          $scope.home.$update({},
              function (data) {
                  growl.success("Home has been updated.");
              },
              function (error) {
                  console.log(error);
                  growl.error("An error occurs while updating the home !");
              }
          );
          $scope.closeThisDialog();
      };
  });

})();
