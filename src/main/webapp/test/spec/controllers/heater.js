'use strict';

describe('Controller: HeaterCtrl', function () {

  // load the controller's module
  beforeEach(module('sirApp'));

  var HeaterCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    HeaterCtrl = $controller('HeaterCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
