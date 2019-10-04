import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';
import { MessageService } from './message.service';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/shared/models/authentication/user.model';
import { Message } from 'src/app/shared/models/content/message.model';

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
export class MessageComponent implements OnInit, OnChanges {

  @Input() currentRoom: number;
  @Input() userLogged: User;
  messages: Message[] = []; 
  previousMessages: Message[] = [];
  headersResp: string[];
  sendMessageForm;

  constructor(
      private messageService: MessageService,
      private formBuilder: FormBuilder,
      private router: Router
  ) {
    this.sendMessageForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.currentRoom.currentValue != null) {
      this.messageService
        .channelSubscription(changes.currentRoom.currentValue)
        // .pipe(map(messages => messages.sort(RoomComponent.descendingByPostedAt)))
        .subscribe(messages => this.messages = messages);       
    }
  }

  // GET HISTORY 10 by 10 or more
  // public getMessagesHistory(currentRoom: number) {
  //   this.messageService.getMessagesHistory(currentRoom).subscribe(data => {
  //     data.body.map(element => this.previousMessages.push(element));
  //   });
  // }

  onSubmit() {
    //this.createMessage(this.sendMessageForm);
    if (this.userLogged != null) {
        console.log("Room id avant insertion dans objet message : "+this.currentRoom);
        let newMessage = new Message( this.userLogged.getId(),
                                      this.sendMessageForm.get("content").value,
                                      null,
                                      true,
                                      null,
                                      true,
                                      this.currentRoom);
        this.messageService.saveNewMessage(newMessage);
    } 
  }

  public createMessage(data: any) : void {
    // A MODIFIER
    //var date = new Date().toJSON().slice(0,10).replace(/-/g,'/');
    // var date = new Date();
    // let newMessage = new Message( new User("pierrho", "", 1),
    //                               data.get("content").value,
    //                               null,
    //                               true,
    //                               null,
    //                               true,
    //                               this.currentRoom);
    // this.messageService.saveNewMessage(newMessage);
  }






  // setMessage(message: Message) {
  //     this.message = message;
  // }

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
