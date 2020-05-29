import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable,throwError } from "rxjs";
import { map,catchError } from "rxjs/operators";
interface Usuario {
  username: string;
  avatar: string;
}



@Injectable({
  providedIn: "root",
})
export class UsuarioService {
  private GET_USUARIO = "http://localhost:8081/api/cliente/me";
  private PUT_CLIENTE_PERSONALES = "http://localhost:8081/api/cliente";

  private token = sessionStorage.getItem("token");
  constructor(private httpClient: HttpClient) {}

  public getUsuario(){
    let header = new HttpHeaders();
    header.set("Authorization", "Bearer " + this.token);
    return this.httpClient.get(this.GET_USUARIO);
  }


  public putCliente(datos: any) {
    return this.httpClient.put(this.PUT_CLIENTE_PERSONALES, datos)
    
  }
}
