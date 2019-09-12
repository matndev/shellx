import { Component, OnInit } from '@angular/core';
import { RoomService } from './room.service';
import { AuthenticationService } from '../../authentication/authentication/authentication.service';
import { Message } from 'src/app/shared/models/content/message.model';
import { Room } from 'src/app/shared/models/content/room.model';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {

  messages: Message[] = []; 
  room: Room;
  headersResp: string[];
  sendMessageForm;

  constructor(
    private authenticationService: AuthenticationService,
    private roomService: RoomService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.sendMessageForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  ngOnInit() {
    // if (this.authenticationService.isAuthenticated()) { // ATTENTION METTRE ! devant
    //   this.router.navigateByUrl('/');
    // } 
    // else {
    //   console.log("Room Component : message service : false"); 
    //   //this.router.navigateByUrl('/login');
      
    //   // EXAMPLE
    //   this.roomService.getRoomWithMessages("1").subscribe(resp => {  
   
    //       const keys = resp.headers.keys();
    //       this.headersResp = keys.map(key => `${key}: ${resp.headers.get(key)}`);

    //       this.room = new Room(resp.body['name'], resp.body['roomAdmin'], resp.body['enabled'], resp.body['modePrivate']);

    //       resp.body['messages'].forEach(element => {
    //             this.messages.push(new Message(element['messageAuthor'],
    //             element['messageContent'],
    //             element['messageDate'],
    //             element['messageEnabled'],
    //             element['messageReceiver'],
    //             element['messageVisible']));
    //       });


    //   });      
    // } 
    // this.roomService.getRoomWithMessages("1").subscribe(data => {
    //   this.room = new Room(data.body['name'], data.body['roomAdmin'], data.body['enabled'], data.body['modePrivate']);
    // });
    this.roomService
      .findAll()
      // .pipe(map(messages => messages.sort(RoomComponent.descendingByPostedAt)))
      .subscribe(messages => this.messages = messages);      
  }

  // public static descendingByPostedAt(message1: Message, message2: Message): number {
  //   return message2.getMessageDate.getTime() - message1.postedAt.getTime();
  // }

  onSubmit() {

  }
}

// ngOnInit() {
//   this.pusherService.messagesChannel.bind('client-new-message', (message) => {
//     this.messages.push(message);
//   });
// }
