import { TestBed } from '@angular/core/testing';

import { DialogresponseService } from './dialogresponse.service';

describe('DialogresponseService', () => {
  let service: DialogresponseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DialogresponseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
