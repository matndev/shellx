import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError, first } from 'rxjs/operators';
import { Message } from 'src/app/shared/models/content/message.model';
import { Router } from '@angular/router';
import { SocketClientService } from 'src/app/core/websocket/socket-client.service';
import * as moment from 'moment';
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
export class MessageService {

  private url = SERVER_HTTPS_URL;

  constructor(
    private http: HttpClient,
    private router: Router,
    private socketClient: SocketClientService
  ) { }

  public subscribeChannel(id: number): Observable<Message[]> {
    return this.socketClient
            .onMessage('/topic/messages/subscribe/'+id)
            .pipe(map(messages => {
                    var newMessages: Message[] = [];
                    if (messages instanceof Array){
                        messages.forEach(e => newMessages.push(new Message(e.messageAuthor, e.messageContent, e.messageDate, e.messageEnabled, e.messageReceiver, e.messageVisible, e.messageRoom)));
                        messages = this.dateManager(messages);
                    }
                    else {
                        newMessages.push(new Message(messages.messageAuthor, messages.messageContent, messages.messageDate, messages.messageEnabled, messages.messageReceiver, messages.messageVisible, messages.messageRoom));
                        newMessages = this.dateManager(newMessages);
                    }
                    return newMessages;
            }));
  }

  private dateManager(messages: any) : Message[] {
    messages.forEach(element => {
      if (element.messageDate != null) {
          var utc = moment.utc(element.messageDate).toDate();
          element.messageDate = moment(utc).local().format('YYYY-MM-DD HH:mm:ss');
      }
    });
    return messages;
  }

  public saveNewMessage(message: Message) : void {
    this.socketClient.send("/app/messages/add", message);
  } 
  
  public getMessagesHistory(currentRoomId: number) : Observable<HttpResponse<Message[]>> {
    return this.http.get<HttpResponse<any>>(this.url+"/messages/get/all/"+currentRoomId, httpOptions)
        .pipe(
          map(obj => {
            var messagesArray: Message[] = [];
            obj.body.forEach(e => 
              messagesArray.push(new Message(e.messageAuthor, e.messageContent, e.messageDate, e.messageEnabled, e.messageReceiver, e.messageVisible, e.messageRoom))
            );
            messagesArray = this.dateManager(messagesArray);
            return obj.clone({
              body: messagesArray
            });
          }),
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
