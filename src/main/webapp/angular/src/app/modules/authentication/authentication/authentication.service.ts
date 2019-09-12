import { Injectable } from '@angular/core';
import { User } from 'src/app/shared/models/user.model';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    //'Authorization': 'Basic ' + btoa('mail:password')
  }), 
  observe: 'response' as 'body'
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private url = "http://localhost:8086";
  private authenticated = false;

  constructor(
    private http: HttpClient
  ) { }

  isAuthenticated() {
    return this.authenticated;
  }

  login(credentials, callback) {
    this.http.post(this.url+'/login', credentials, { observe: 'response', withCredentials: true }).subscribe(response => {
      return callback && callback();
    });
  }

  register(data: any): Observable<HttpResponse<string>> {
    return this.http.post<HttpResponse<string>>(this.url+'/register/', data, httpOptions)
        .pipe(
            catchError(this.handleError) // .bind(this) used to pass the context
        );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  };
    
}
