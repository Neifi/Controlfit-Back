import { Component, OnInit } from '@angular/core';
import { DialogresponseService } from '../services/dialogresponse.service';

@Component({
  selector: 'app-confirmar-borrar-cliente',
  templateUrl: './confirmar-borrar-cliente.component.html',
  styleUrls: ['./confirmar-borrar-cliente.component.css']
})
export class ConfirmarBorrarClienteComponent implements OnInit {

  constructor(private dialogResponseService:DialogresponseService) { }

  ngOnInit(): void {
  }

  public aceptar(){
    this.dialogResponseService.$aceptado = true;
  }
}
