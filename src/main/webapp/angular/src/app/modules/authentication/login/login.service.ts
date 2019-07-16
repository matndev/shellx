/*import { Injectable } from '@angular/core';
import { ConfigHttpService } from 'src/app/core/http/config-http.service';
import { User } from 'src/app/shared/models/user.model';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json', // x-www-form-urlencoded
    'Accept': 'application/json',
    'Access-Control-Allow-Origin': 'http://localhost:8086'
    //'Authorization': 'Basic Auth'
  })
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url = "http://localhost:8086";
  authenticated = false;
  user: User;

  constructor(
    private http: HttpClient
  ) { }

  authenticate(userLogin: User): Observable<User> {
    return this.http.post<User>(this.url+'/login/', userLogin, httpOptions)
                    .pipe(
                        //retry(3), //retry a failed request up to 3 times
                        catchError(this.handleError('login', userLogin))
                    );
                    //.subscribe((data: User) => this.user = data);
  }*/

  /**
  * Handle Http operation that failed.
  * Let the app continue.
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
 /*
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      //this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }  
}*/
