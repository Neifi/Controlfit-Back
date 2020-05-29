import { Component, OnInit } from "@angular/core";
import { UsuarioService } from "app/service/usuario/usuario.service";
import { Cliente } from "app/model/Cliente";
import { FormGroup, FormControl, Validators } from "@angular/forms";

@Component({
  selector: "app-user-profile",
  templateUrl: "./user-profile.component.html",
  styleUrls: ["./user-profile.component.css"],
})
export class UserProfileComponent implements OnInit {
  public cliente = new Cliente();


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

  constructor(private usuarioService: UsuarioService) {
    this.usuarioService.getUsuario().subscribe((data: any) => {
      this.cliente = data;
      console.log(data);
      
      this.form.get("email").setValue(data["email"]);
      this.form.get("fecha_inscripcion").setValue(data["fecha_inscripcion"]);
      this.form.get("dni").setValue(data["dni"]);
      this.form.get("nombre").setValue(data["nombre"]);
      this.form.get("apellidos").setValue(data["apellidos"]);
      this.form.get("calle").setValue(data["calle"]);      
      this.form.get("ciudad").setValue(data["ciudad"]);
      this.form.get("provincia").setValue(data["provincia"]);
      this.form.get("codigo_postal").setValue(parseInt( data["codigo_postal"]));
      this.form.get("fecha_nacimiento").setValue(data["fecha_nacimiento"]);
      console.log(this.form.value);
    });
  }

  public updateUser() {
    console.log(this.form.value);
    this.usuarioService.putCliente(this.form.value).subscribe((data) => {
      this.cliente = this.form.value;
      console.log(this.form.get("direccion_postal").value);

    });
  }

  ngOnInit() {}
}
