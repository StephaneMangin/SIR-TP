(function () {

  'use strict';

  /**
   * @ngdoc function
   * @name sirApp.controller:DeviceCtrl
   * @description
   * # DeviceCtrl
   * Controller of the sirApp
   */
  angular
    .module('sirApp')
    .controller('DeviceCtrl', function ($rootScope, $route, $scope, $routeParams, $http, $location, Device, ngDialog, $filter, ngTableParams, growl) {

      $scope.getDevice = function() {
        $scope.device = Device.get({deviceId: $routeParams.id}, function (response) {});
      };

      $scope.getDevices = function () {
          Device.query(function (response) {
            $scope.devices = response;
              $scope.tableParams = new ngTableParams({
                  page: 1,            // show first page
                  count: 10          // count per page
              }, {
                  total: $scope.devices.length, // length of data
                  getData: function ($defer, params) {
                      var orderedData = params.filter() ? $filter('filter')($scope.devices, params.filter()) : $scope.devices;
                      orderedData = params.sorting() ? $filter('orderBy')(orderedData, $scope.tableParams.orderBy()) : orderedData;
                      $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));

                      params.total(orderedData.length);
                      $defer.resolve($scope.devices);
                  }
              });
          });
      };

      $scope.delete = function (id) {
        Device.get({deviceId: id}, function (response) {
          response.$delete({deviceId: response.id}, function () {
            growl.success("Device deleted.");
                $location.path('/devices');
            });
        });
      };

      $scope.deleteDevices = function() {
          angular.forEach($scope.checkboxes.items, function(value, key) {
              var checked = 0;
              angular.forEach($rootScope.devices, function(item) {
                  checked   +=  ($scope.checkboxes.items[item.id]) || 0;
              });
              $scope.current = 0;
              Device.get({deviceId: key}, function (response) {
                  $scope.current = $scope.current + 1;
                  console.log(response);
                  response.$delete({deviceId: key});
                  if($scope.current == checked) {
                      growl.success("Devices deleted.");
                      $route.reload();
                  }
              });
          });
      };

      $scope.addDevice = function () {
          ngDialog.open({
              template: 'app/views/deviceAdd.html',
              controller: 'DeviceCreateCtrl',
              appendTo: '#dialog-device',
              scope: $scope
          });
      };

      $scope.updateDevice = function () {
          ngDialog.open({
              template: 'app/views/deviceAdd.html',
              controller: 'DeviceUpdateCtrl',
              appendTo: '#dialog-device',
              scope: $scope
          });
      };
  })
  .controller('DeviceCreateCtrl', function ($rootScope, $scope, $routeParams, $http, $location, Device, Person, ngDialog, growl) {
      $scope.titleNgDialog = "Add a device";

		$scope.persons = Person.query();

      $scope.dateOptions = {
          startingDay: 1
        };

      $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
      };
      $scope.device = new Device();

      $scope.submit = function () {

          $scope.device.$save(
              function (data) {
                  growl.success("Device has been created.");
              },
              function (error) {
                  console.log(error);
                  growl.error("An error occurs while creating the device !");
              }
          );
          $scope.closeThisDialog();
      };
  })
  .controller('DeviceUpdateCtrl', function ($rootScope, $scope, $routeParams, $http, $location, Device, ngDialog, growl) {
      $scope.titleNgDialog = "Update a device";
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
          delete $scope.device.devices;
          $scope.device.$update({},
              function (data) {
                  growl.success("Device has been updated.");
              },
              function (error) {
                  console.log(error);
                  growl.error("An error occurs while updating the device !");
              }
          );
          $scope.closeThisDialog();
      };
  });

})();
