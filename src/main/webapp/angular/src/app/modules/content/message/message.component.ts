import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';

/*const httpOptions = {
  headers: new HttpHeaders({
//    'Content-Type':  'application/json', // x-www-form-urlencoded
//    'Accept': 'application/json',
    'Access-Control-Allow-Origin': 'http://localhost:8086'
  })
};*/

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  /*authenticate(userLogin: User): Observable<User> {
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

}
