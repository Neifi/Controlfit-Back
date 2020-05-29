import { Component, OnInit, NgModule } from "@angular/core";
import { FormControl } from "@angular/forms";
import { LoginServiceService } from "./services/login-service.service";
import { Router, ActivatedRouteSnapshot } from "@angular/router";
import { DashboardComponent } from "app/dashboard/dashboard.component";
import { decode } from "jwt-decode";
@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
  providers: [LoginServiceService],
})
export class LoginComponent implements OnInit {
  isUsed: boolean;
  inputPassword = new FormControl("inputUsername");
  inputUsername = new FormControl("inputUsername");
  constructor(
    private loginService: LoginServiceService,
    private router: Router
  ) {}

  username = "";
  password = "";
  role: any;
  invalidLogin = false;

  onTextChange() {
    this.isUsed = true;
    console.log(this.isUsed);
  }

  login(route: ActivatedRouteSnapshot) {
    // const token = sessionStorage.getItem("token");
    // this.role = decode(token).role;

    this.loginService.authenticate(this.username, this.password).subscribe(
      (data) => {
        this.invalidLogin = false;
        this.role = sessionStorage.getItem;
        this.router.navigate(["dashboard"]);
      },
      (error) => {
        this.invalidLogin = true;
      }
    );
  }
  ngOnInit(): void {}
}
