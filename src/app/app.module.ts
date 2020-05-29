import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule, APP_INITIALIZER } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpModule } from "@angular/http";
import { RouterModule } from "@angular/router";

import { AppRoutingModule } from "./app.routing";
import { ComponentsModule } from "./components/components.module";

import { AppComponent } from "./app.component";

import { AgmCoreModule } from "@agm/core";
import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";
import {
  HTTP_INTERCEPTORS,
  HttpClientModule,

} from "@angular/common/http";
import { BasicAuthHttpInterceptorService } from "./service/basic-auth-http-interceptor.service";
import { AuthGuard } from "./components/login/services/auth.guard.service";
import { UsuarioService } from "./service/usuario/usuario.service";
import { ConfirmarBorrarClienteComponent } from './dialogs/confirmar-borrar-cliente/confirmar-borrar-cliente.component';

export function parameterProviderFactory(provider: UsuarioService) {
  return () => provider.getUsuario();
}


@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    ComponentsModule,
    RouterModule,
    AppRoutingModule,
    HttpClientModule,
    
    

    AgmCoreModule.forRoot({
      apiKey: "YOUR_GOOGLE_MAPS_API_KEY",
    }),
  ],
  declarations: [AppComponent, AdminLayoutComponent, ConfirmarBorrarClienteComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: BasicAuthHttpInterceptorService,
      multi: true,
    },
    {
      provide:APP_INITIALIZER,
      useFactory: parameterProviderFactory,
      deps:[UsuarioService],
      multi: true
    },
    

    AuthGuard,
    UsuarioService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
