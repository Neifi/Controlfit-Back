import { Component, OnInit } from "@angular/core";

declare const $: any;
declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: "/dashboard", title: "Panel", icon: "dashboard", class: "" },
  { path: "/user-profile", title: "Mi perfil", icon: "account_circle", class: "" },

  {
    path: "/table-list",
    title: "Gestión de usuarios",
    icon: "content_paste",
    class: "",
  },
  {
    path: "/typography",
    title: "Mis entrenamientos",
    icon: "fitness_center",
    class: "",
  },
];

@Component({
  selector: "app-sidebar",
  templateUrl: "./sidebar.component.html",
  styleUrls: ["./sidebar.component.css"],
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor() {}

  ngOnInit() {
    this.menuItems = ROUTES.filter((menuItem) => menuItem);
  }
  isMobileMenu() {
    if ($(window).width() > 991) {
      return false;
    }
    return true;
  }
}
