'use strict';

describe('Service: heater', function () {

  // load the service's module
  beforeEach(module('sirApp'));

  // instantiate service
  var heater;
  beforeEach(inject(function (_heater_) {
    heater = _heater_;
  }));

  it('should do something', function () {
    expect(!!heater).toBe(true);
  });

});
