import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, first, map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/authentication/user.model';
import { SocketClientService } from 'src/app/core/websocket/socket-client.service';
import { SERVER_HTTPS_URL } from 'src/environments/environment';

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

  private url = SERVER_HTTPS_URL;

  constructor(
    private http: HttpClient,
    private router: Router,
    private socketClient: SocketClientService
  ) { }

  // No interfaces used because Typescript convert it in JS at runtime
  public getUsersByRoomId(id: number) : Observable<HttpResponse<User[]>> {
    return this.http.get<HttpResponse<any>>(this.url+"/user/room/"+id, httpOptions)
        .pipe(
            map(obj => {
              var userArray: User[] = [];
              obj.body.forEach(e => 
                userArray.push(new User(e.username, null, e.id, e.role, e.avatar))
              );
              return obj.clone({
                body: userArray
              });
            }),
            catchError(this.handleError.bind(this)) // .bind(this) used to pass the context
        );
  }

  public subscribeUserlist(id: number) : Observable<User[]> {
    return this.socketClient
            .onMessage('/topic/user/subscribe/'+id)
            .pipe(map(user => {
                    var newUsers: User[] = [];
                    if (user instanceof Array){
                        user.forEach(e => newUsers.push(new User(e.username, e.email, e.id, e.role, e.avatar)));
                    }
                    else {
                        newUsers.push(new User(user.username, user.email, user.id, user.role, user.avatar));
                    }
                    return newUsers;
            }));
  }

  public invite(user_id: number, room_id: number) : Observable<HttpResponse<boolean>> {
    // this.socketClient.send("/app/user/invite/"+idRoom, idUser);
    var obj = { idRoom: room_id, idUser: user_id };
    return this.http.post<HttpResponse<boolean>>(this.url+"/user/invite", JSON.stringify(obj), httpOptions)
        .pipe(
          catchError(this.handleError.bind(this)) // .bind(this) used to pass the context
        );    
  }

  // public getUsersByRoomId(id: number): Observable<User[]> {
  //   return this.socketClient
  //     .onMessage('/app/user/subscribe/'+id)
  //     .pipe(first(), map(messages => {
  //         let users: User[] = [];
  //         messages.forEach(e => {
  //           users.push(new User(e.username, null, e.idUser, e.role))
  //         });
  //         return users;
  //     }));
  // }

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
