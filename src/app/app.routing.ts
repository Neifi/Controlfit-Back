import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BrowserModule } from "@angular/platform-browser";
import { Routes, RouterModule, CanActivate } from "@angular/router";

import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";
import { LoginComponent } from "./components/login/login.component";
import { AuthGuard } from "./components/login/services/auth.guard.service";
<<<<<<< HEAD

const routes: Routes = [
=======
import { AddClienteComponent } from "./dialogs/add-cliente/add-cliente.component";

const routes: Routes = [
  {
    path: "add",
    component: AddClienteComponent,
    pathMatch: "full",
    
  },
>>>>>>> branch 'master' of https://github.com/Neifi/GestionGymAPI
  {
    path: "login",
    component: LoginComponent,
    pathMatch: "full",
    
  },
  {
    path: "",
    redirectTo: "login",
    pathMatch: "full",
    canActivate: [AuthGuard],
  },
  {
    path: "",
    component: AdminLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: "",
        loadChildren:
          "./layouts/admin-layout/admin-layout.module#AdminLayoutModule",
        canActivate: [AuthGuard],
      },
    ],
  },
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes, {
      useHash: true,
    }),
  ],
  exports: [],
})
export class AppRoutingModule {}
