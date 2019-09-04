import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Message } from 'src/app/shared/models/content/message.model';
import { Room } from 'src/app/shared/models/content/room.model';
import { Router } from '@angular/router';

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
export class RoomService {

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  getRoom(id: string): Observable<HttpResponse<Room>> {
    return this.http.get<HttpResponse<Room>>("http://localhost:8086/rooms/"+id, httpOptions);
  }

  getRoomWithMessages(id: string): Observable<HttpResponse<Message[]>> {
    return this.http.get<HttpResponse<Message[]>>("http://localhost:8086/rooms/c/"+id, httpOptions)
        .pipe(
            catchError(this.handleError.bind(this)) // .bind(this) used to pass the context
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
      if (error.error == "token-expired") {
        this.router.navigateByUrl('/login');
      }
    }
    return throwError(
      'Something bad happened; please try again later.');
  };
}
