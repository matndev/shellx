import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError, first } from 'rxjs/operators';
import { Message } from 'src/app/shared/models/content/message.model';
import { Room } from 'src/app/shared/models/content/room.model';
import { Router } from '@angular/router';
import { SocketClientService } from 'src/app/core/websocket/socket-client.service';
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
export class RoomService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private socketClient: SocketClientService
  ) { }

  getRoom(id: number): Observable<HttpResponse<Room>> {
    return this.http.get<HttpResponse<Room>>("http://localhost:8086/rooms/get/"+id, httpOptions);
  }

  // getRoomWithMessages(id: string): Observable<HttpResponse<Message[]>> {
  //   return this.http.get<HttpResponse<Message[]>>("http://localhost:8086/rooms/c/"+id, httpOptions)
  //       .pipe(
  //           catchError(this.handleError.bind(this)) // .bind(this) used to pass the context
  //       );
  // }

  public getRoomsByUserId(id: string) : Observable<HttpResponse<Room[]>> {
    return this.http.get<HttpResponse<Room[]>>("http://localhost:8086/rooms/get/rooms/"+id, httpOptions)
        .pipe(
          catchError(this.handleError.bind(this)) // .bind(this) used to pass the context
        );
  }

  public getUsersByRoomId(id: number) : Observable<HttpResponse<User[]>> {
    return this.http.get<HttpResponse<User[]>>("http://localhost:8086/rooms/get/users/"+id, httpOptions)
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

  public channelSubscription(id: number): Observable<Message[]> {
    return this.socketClient
      .onMessage('/topic/room/'+id)
      .pipe(first(), map(messages => messages.map(RoomService.getMessages)));
  }

  private static getMessages(message: any): Message {
    const postedAt = new Date(message['postedAt']);
    return {...message, postedAt};
  }  

  public save(message: Message) : void {
    this.socketClient.send("/app/message/add", message);
  }
}
