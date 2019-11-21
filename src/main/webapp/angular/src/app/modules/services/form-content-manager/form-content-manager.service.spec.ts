import { TestBed } from '@angular/core/testing';

import { FormContentManagerService } from './form-content-manager.service';

describe('FormContentManagerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FormContentManagerService = TestBed.get(FormContentManagerService);
    expect(service).toBeTruthy();
  });
});
