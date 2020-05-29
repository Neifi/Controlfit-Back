<<<<<<< HEAD
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

import { FooterComponent } from "./footer/footer.component";
import { SidebarComponent } from "./sidebar/sidebar.component";
import { LoginComponent } from "./login/login.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
@NgModule({
  imports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule],
  declarations: [FooterComponent, SidebarComponent, LoginComponent],
exports: [FooterComponent, SidebarComponent,MatDialogModule,MatButtonModule,MatButtonToggleModule],
=======
import { NgModule, CUSTOM_ELEMENTS_SCHEMA,NO_ERRORS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

import { FooterComponent } from "./footer/footer.component";
import { SidebarComponent } from "./sidebar/sidebar.component";
import { LoginComponent } from "./login/login.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
@NgModule({
  imports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule],
  declarations: [FooterComponent, SidebarComponent, LoginComponent],
exports: [FooterComponent, SidebarComponent,MatDialogModule,MatButtonModule,MatButtonToggleModule],
schemas:[NO_ERRORS_SCHEMA,CUSTOM_ELEMENTS_SCHEMA],

>>>>>>> branch 'master' of https://github.com/Neifi/GestionGymAPI
})
export class ComponentsModule {}
