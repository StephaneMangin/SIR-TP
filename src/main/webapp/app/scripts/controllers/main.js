(function () {

    'use strict';

    /**
     * Controller for client route
     */
    angular
        .module('sirApp')
        .controller('MainCtrl', function ($rootScope, $route, $scope, $routeParams, $http, $location, Person, Home, $filter, ngTableParams) {

            $scope.initHomepage = function () {
            	Person.query(function (response) {
                    $scope.persons = response;
                    $scope.tablePersons = new ngTableParams({
                        page: 1,            // show first page
                        count: 5          // count per page
                    }, {
                        total: $scope.persons.length, // length of data
                        getData: function ($defer, params) {
                            params.total($scope.persons.length);
                            $defer.resolve($scope.persons);
                        }
                    });
                });
                Home.query(function (response) {
                    $scope.homes = response;
                    $scope.tableHomes = new ngTableParams({
                        page: 1, // show first page
                        count: 5 // count per page
                    },{
                        total: $scope.homes.length, // length of data
                        getData: function ($defer, params) {

                            params.total($scope.homes.length);
                            $defer.resolve($scope.homes);
                        }
                    });
                });
            }



        });
})();
