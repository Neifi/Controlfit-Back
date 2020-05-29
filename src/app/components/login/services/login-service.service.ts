import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { map } from "rxjs/operators";
@Injectable({
  providedIn: "root",
})
export class LoginServiceService {
  private url = "http://localhost:8081/auth/login";

  constructor(public http: HttpClient) {}

  public authenticate(username: string, password: string) {
    return this.http
      .post<any>(this.url, { username, password })
      .pipe(
        map((userData) => {
          sessionStorage.setItem("username", username);
          let token = "Bearer " + userData.token;
          sessionStorage.setItem("token", token);
          return userData;
        })
      );
  }

  isLoged() {
    let user = sessionStorage.getItem("username");
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem("username");
  }
}
