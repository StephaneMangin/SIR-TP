'use strict';

describe('Controller: ElectronicdeviceCtrl', function () {

  // load the controller's module
  beforeEach(module('sirApp'));

  var ElectronicdeviceCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ElectronicdeviceCtrl = $controller('ElectronicdeviceCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
