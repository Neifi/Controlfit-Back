import { CanActivate, Router, ActivatedRouteSnapshot } from "@angular/router";
import { LoginServiceService } from "./login-service.service";
import { Injectable } from "@angular/core";
import decode from "jwt-decode";
import { UsuarioService } from "app/service/usuario/usuario.service";
import { LoginComponent } from "../login.component";

@Injectable({
  providedIn: "root",
})
export class AuthGuard implements CanActivate {
  constructor(
    private loginService: LoginServiceService,
    private usuarioService:UsuarioService,
    public router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data.role;
    const token = sessionStorage.getItem("token");
    //Decodificar token
    const tokenPayload = decode(token);
    if (!this.loginService.isLoged()) {
      tokenPayload.role != expectedRole;
      this.router.navigate([LoginComponent]);
      return false;
    }else{

      return true;
    }
  }

}
