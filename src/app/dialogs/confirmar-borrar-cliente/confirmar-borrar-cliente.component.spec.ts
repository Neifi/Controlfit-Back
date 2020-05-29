import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmarBorrarClienteComponent } from './confirmar-borrar-cliente.component';

describe('ConfirmarBorrarClienteComponent', () => {
  let component: ConfirmarBorrarClienteComponent;
  let fixture: ComponentFixture<ConfirmarBorrarClienteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmarBorrarClienteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmarBorrarClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
