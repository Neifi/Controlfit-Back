import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegistroHorario } from 'app/model/RegistroHorario';

@Injectable({
  providedIn: 'root'
})
export class RegistrohorarioService {

  private registroHorario = new RegistroHorario();
  private registrosHOrarios = [RegistroHorario];

  constructor(private httpClient:HttpClient ) { }
  private URL = "http://localhost:8081/api/horario/";

  public getRegistros(){
    
    return this.httpClient.get<RegistroHorario[]>(this.URL);
  }
  public getRegistroPorIdUsuario(id:number){
    console.log(id);
    id++;
    return this.httpClient.get<RegistroHorario[]>(this.URL+id);
  }

  
  

  
}
