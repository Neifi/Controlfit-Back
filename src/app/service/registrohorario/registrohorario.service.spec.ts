import { TestBed } from '@angular/core/testing';

import { RegistrohorarioService } from './registrohorario.service';

describe('RegistrohorarioService', () => {
  let service: RegistrohorarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegistrohorarioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
