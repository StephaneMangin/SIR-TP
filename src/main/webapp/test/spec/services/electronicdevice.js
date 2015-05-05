'use strict';

describe('Service: electronicDevice', function () {

  // load the service's module
  beforeEach(module('sirApp'));

  // instantiate service
  var electronicDevice;
  beforeEach(inject(function (_electronicDevice_) {
    electronicDevice = _electronicDevice_;
  }));

  it('should do something', function () {
    expect(!!electronicDevice).toBe(true);
  });

});
