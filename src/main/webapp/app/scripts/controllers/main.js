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
                            var orderedData = params.filter() ? $filter('filter')($scope.persons, params.filter()) : $scope.persons;
                            orderedData = params.sorting() ? $filter('orderBy')(orderedData, params.orderBy()) : orderedData;
                            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));

                            params.total(orderedData.length);
                            $defer.resolve(orderedData);
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
                            var orderedData = params.filter() ? $filter('filter')($scope.homes, params.filter()) : $scope.homes;
                            orderedData = params.sorting() ? $filter('orderBy')(orderedData, params.orderBy()) : orderedData;
                            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));

                            params.total(orderedData.length);
                            $defer.resolve(orderedData);
                        }
                    });
                });
            }



        });
})();
