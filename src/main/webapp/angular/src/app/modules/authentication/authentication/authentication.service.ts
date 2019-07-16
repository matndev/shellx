import { Injectable } from '@angular/core';
import { User } from 'src/app/shared/models/user.model';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  url = "http://localhost:8086";
  authenticated = false;
  user: User;

  constructor(
    private http: HttpClient
  ) { }


  authenticate(credentials, callback) {

      const headers = new HttpHeaders(credentials ? {
          authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
      } : {});

      /*if (credentials) {
        httpOptions.headers = httpOptions.headers.set('Authorization', 'Basic ' + btoa(credentials.username + ':' + credentials.password));
      }*/

      this.http.get(this.url+'/message/update', {headers: headers}).subscribe(response => {
          if (response['name']) {
              this.authenticated = true;
          } else {
              this.authenticated = false;
          }
          return callback && callback();
      });

  }
}
