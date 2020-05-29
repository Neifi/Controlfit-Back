import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Cliente } from 'app/model/Cliente';
@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private  URL = "http://localhost:8081/api/cliente/";
  constructor(private httpClient:HttpClient) {

   }


  public getClientes(){
    
    return this.httpClient.get<Cliente[]>(this.URL);
  }
  public getCliente(id:number){
    console.log(id);
    id++;
    return this.httpClient.get<Cliente[]>(this.URL+id);
  }


  public putCliente(datos: any,id:number) {
    id++;
    console.log(id);
    console.log(datos);
    
    return this.httpClient.put(this.URL+id, datos)
    
  }

<<<<<<< HEAD
=======
  public postCliente(cliente:Cliente){
    return this.httpClient.post(this.URL,cliente);
  }

>>>>>>> branch 'master' of https://github.com/Neifi/GestionGymAPI
}
