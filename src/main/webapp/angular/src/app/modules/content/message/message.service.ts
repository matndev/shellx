import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError, first } from 'rxjs/operators';
import { Message } from 'src/app/shared/models/content/message.model';
import { Router } from '@angular/router';
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
export class MessageService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private socketClient: SocketClientService
  ) { }

  public channelSubscription(id: number): Observable<Message[]> {
    return this.socketClient
      .onMessage('/app/messages/subscribe/'+id)
      .pipe(first(), map(messages => messages.map(MessageService.getMessages)));
  }

  private static getMessages(message: any): Message {
    const postedAt = new Date(message['postedAt']);
    return {...message, postedAt};
  }  

  public saveNewMessage(message: Message) : void {
    this.socketClient.send("/app/messages/add", message);
  } 
  
  public getMessagesHistory(currentRoomId: number) : Observable<HttpResponse<Message[]>> {
    return this.http.get<HttpResponse<Message[]>>("http://localhost:8086/messages/get/all/"+currentRoomId, httpOptions)
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