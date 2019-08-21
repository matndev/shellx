import { Injectable } from '@angular/core';
import { User } from 'src/app/shared/models/user.model';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private url = "http://localhost:8086";
  private authenticated = false;
  //user: User;

  constructor(
    private http: HttpClient,
    private cookieService: CookieService
  ) { }

  isAuthenticated() {
    return this.authenticated;
  }

  authenticate(credentials, callback) {

      /*const headers = new HttpHeaders(credentials ? {
          authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
      } : {});*/

      /*if (credentials) {
        httpOptions.headers = httpOptions.headers.set('Authorization', 'Basic ' + btoa(credentials.username + ':' + credentials.password));
      }*/

      //this.http.get(this.url+'/user/login/1', {headers: headers}).subscribe(response => {
      this.http.get(this.url+'/login/').subscribe(response => {
          if (response['username']) {
              console.log(response['username']);
              this.authenticated = true;
          } else {
              console.log("Authentication failed");
              this.authenticated = false;
          }
          return callback && callback();
      });

  }

  login(credentials, callback) {
    this.http.post(this.url+'/login', credentials, { observe: 'response', withCredentials: true }).subscribe(response => {
      // if (response['username']) {
      //     console.log(response['username']);
      //     this.authenticated = true;
      // } else {
      //     console.log("Authentication failed");
      //     this.authenticated = false;
      // }
      //response.headers ? console.log("cookie présent") : console.log("Cookie non présent");

      return callback && callback();
  });
  }
}
