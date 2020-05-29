import { Component, OnInit } from "@angular/core";
import { ClienteService } from "app/service/cliente/cliente.service";
import { Cliente } from "app/model/Cliente";
import { FormGroup, FormControl } from "@angular/forms";
import { RegistrohorarioService } from "app/service/registrohorario/registrohorario.service";
import { RegistroHorario } from "app/model/RegistroHorario";
import {MatDialogModule, MatDialog} from '@angular/material/dialog';
import { ConfirmarBorrarClienteComponent } from "app/dialogs/confirmar-borrar-cliente/confirmar-borrar-cliente.component";

@Component({
  selector: "app-table-list",
  templateUrl: "./table-list.component.html",
  styleUrls: ["./table-list.component.css"],
})

export class TableListComponent implements OnInit {

  public clientes=[Cliente];
  public cliente = new Cliente();
  public registrosHorario = [RegistroHorario];
  public form = new FormGroup({
    email: new FormControl(),
    fecha_inscripcion: new FormControl(""),
    dni: new FormControl(""),
    nombre: new FormControl(""),
    apellidos: new FormControl(""),
    calle: new FormControl(""),
    ciudad: new FormControl(""),
    provincia: new FormControl(""),
    codigo_postal: new FormControl(""),
    fecha_nacimiento: new FormControl(""),
  });
  constructor(private clienteService:ClienteService,private registroHorarioService:RegistrohorarioService,public dialog: MatDialog) {
   this.getClientes();
  }

  public getClientes(){
    this.clienteService.getClientes().subscribe((data:[any]) =>{
      
      this.clientes = data;
    })
  }


 public getSelectedUser(id:number){
    this.clienteService.getCliente(id).subscribe((data:any) =>{
      this.cliente = data;
      this.form.get("email").setValue(data["email"]);
      this.form.get("fecha_inscripcion").setValue(data["fecha_inscripcion"]);
      this.form.get("dni").setValue(data["dni"]);
      this.form.get("nombre").setValue(data["nombre"]);
      this.form.get("apellidos").setValue(data["apellidos"]);
      this.form.get("calle").setValue(data["calle"]);
      this.form.get("ciudad").setValue(data["ciudad"]);
      this.form.get("provincia").setValue(data["provincia"]);
      this.form.get("codigo_postal").setValue(data["codigo_postal"]);
      this.form.get("fecha_nacimiento").setValue(data["fecha_nacimiento"]);
      console.log(this.cliente);
      localStorage.setItem("id_cliente",id.toString());
      this.getRegistrosHorarios(id);
      
    });
  }

  public selected(i){
    localStorage.setItem("id_cliente",i);
  }
  public updateCliente() {
    let id = localStorage.getItem("id_cliente");
    this.clienteService.putCliente(this.form.value,parseInt(id)).subscribe((data) => {
      this.cliente = this.form.value;
      this.getClientes();
    });
  }

  public getRegistrosHorarios(id){
    
    this.registroHorarioService.getRegistroPorIdUsuario(parseInt(id)).subscribe((data:[any]) =>{
      console.log(data);
      
      this.registrosHorario = data;
    })
  }
  public deleteCliente(){
  }



  public confirmarBorrar(){
    this.dialog.open(ConfirmarBorrarClienteComponent);

  }
  ngOnInit() {}
}
