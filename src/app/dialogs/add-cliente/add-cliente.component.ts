import { Component, OnInit } from '@angular/core';
import { Cliente } from 'app/model/Cliente';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { matFormFieldAnimations } from '@angular/material/form-field';
import { ClienteService } from 'app/service/cliente/cliente.service';


@Component({
  selector: 'app-add-cliente',
  templateUrl: './add-cliente.component.html',
  styleUrls: ['./add-cliente.component.css']
})
export class AddClienteComponent implements OnInit {

  
  public cliente = new Cliente();
  private dateRegex: string = '^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$';

  public form = new FormGroup({
    email: new FormControl("",[Validators.email,Validators.required]),
    fecha_inscripcion: new FormControl("",[Validators.required]),
    dni: new FormControl("",[Validators.pattern('[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]'),Validators.required]),
    nombre: new FormControl("",[Validators.required]),
    apellidos: new FormControl("",[Validators.required]),
    calle: new FormControl("",[Validators.required]),
    ciudad: new FormControl("",[Validators.required]),
    provincia: new FormControl("",[Validators.required]),
    codigo_postal: new FormControl("",[Validators.required]),
    fecha_nacimiento: new FormControl("",[Validators.required]),
  });

  constructor(public clienteService:ClienteService) { }

  saveCliente(){
    this.cliente = this.form.value;
    console.log(this.cliente);
    this.clienteService.postCliente(this.cliente).subscribe();
  }

  ngOnInit(): void {
  }

}
