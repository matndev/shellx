import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, first, map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/authentication/user.model';
import { SocketClientService } from 'src/app/core/websocket/socket-client.service';

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
export class UserlistService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private socketClient: SocketClientService
  ) { }

  // public getUsersByRoomId(id: number) : Observable<HttpResponse<User[]>> {
  //   return this.http.get<HttpResponse<User[]>>("http://localhost:8086/rooms/get/users/"+id, httpOptions)
  //       .pipe(
  //         catchError(this.handleError.bind(this)) // .bind(this) used to pass the context
  //       );
  // }

  // public subscribeUserlist(id: number) : Observable<User[]> {
  //   return this.socketClient.onMessage('/topic/user/room/'+id);
  // }

  public getUsersByRoomId(id: number): Observable<User[]> {
    return this.socketClient
      .onMessage('/app/user/subscribe/'+id)
      .pipe(first(), map(messages => {
          let users: User[] = [];
          messages.forEach(e => {
            users.push(new User(e.username, null, e.idUser, e.role))
          });
          return users;
      }));
  }

  public add(idRoom: number, idUser: string) : void {
    var userToBeAdded = new User(null, null, +idUser);
    this.socketClient.send("/app/user/add/"+idRoom, userToBeAdded);
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
