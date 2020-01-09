import { Injectable, ModuleWithComponentFactories, OnInit, OnDestroy } from '@angular/core';
import { UserLogin } from 'src/app/shared/models/authentication/user-login.model';
import { Observable, of, throwError, Subject } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import * as moment from 'moment';
import { User } from 'src/app/shared/models/authentication/user.model';

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
export class AuthenticationService implements OnInit, OnDestroy {

  private url = "http://localhost:8086";
  // private authenticated = false;
  private authenticatedState = new Subject<boolean>();
  public authenticatedObs = this.authenticatedState.asObservable();
  private cookieExpValue = '';

  constructor(
    private http: HttpClient,
    private cookieService: CookieService
  ) { }

  ngOnInit() {
    var initStateAuth = this.isAuthenticated();
  }

  ngOnDestroy() {
    this.authenticatedState.next();
    this.authenticatedState.complete();
  }  

  isAuthenticated() : boolean {
    // console.log("Expiration cookie : "+this.cookieService.get('_exp'));
    // console.log("Current time : "+moment().format());
    // console.log("Expiration comparison :");
    var exp = moment(this.cookieService.get('_exp')).diff(moment().format());
    console.log(exp);
    var isAuthValid = (exp > 30000) ? true : false;
    this.authenticatedState.next(isAuthValid);
    return isAuthValid;
  }

  getCurrentUserInfos() : User {
    if (this.isAuthenticated()) {
      var obj = JSON.parse(localStorage.getItem("user"));
      return new User(obj.username, obj.email, obj.id, obj.role, obj.avatar);
    }
    else {
      return null;
    }
  }

  login(credentials, callback) {
    this.http.post(this.url+'/login', credentials, { observe: 'response', withCredentials: true }).subscribe(response => {
      localStorage.setItem("user", JSON.stringify(response.body));
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
