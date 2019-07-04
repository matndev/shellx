import { TestBed } from '@angular/core/testing';

import { ConfigHttpService } from './config-http.service';

describe('ConfigHttpService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConfigHttpService = TestBed.get(ConfigHttpService);
    expect(service).toBeTruthy();
  });
});
