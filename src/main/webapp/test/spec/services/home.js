'use strict';

describe('Service: home', function () {

  // load the service's module
  beforeEach(module('sirApp'));

  // instantiate service
  var home;
  beforeEach(inject(function (_home_) {
    home = _home_;
  }));

  it('should do something', function () {
    expect(!!home).toBe(true);
  });

});
