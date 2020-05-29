import { Injectable } from '@angular/core';
import { Subject } from "rxjs/Subject";

@Injectable({
  providedIn: 'root'
})
export class DialogresponseService {




  private aceptado:boolean = false;

  private aceptarSubject = new Subject<void>();
  public aceptar$ = this.aceptarSubject.asObservable();

  private aceptar(){
    
      this.aceptarSubject.next();
    
  }

  public set $aceptado(value: boolean ) {
		this.aceptado = value;
	}

  constructor() { }
}
