import { Component, OnInit } from '@angular/core';
import { RoomService } from './room.service';
import { AuthenticationService } from '../../authentication/authentication/authentication.service';
import { Message } from 'src/app/shared/models/content/message.model';
import { Room } from 'src/app/shared/models/content/room.model';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

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
    if (!this.authenticationService.isAuthenticated()) {
      this.router.navigateByUrl('/');
    } 
    else {
      console.log("Room Component : message service : false"); 
      //this.router.navigateByUrl('/login');
      
      // EXAMPLE
      this.roomService.getRoomWithMessages("1").subscribe(resp => {  
   
          const keys = resp.headers.keys();
          this.headersResp = keys.map(key => `${key}: ${resp.headers.get(key)}`);

          this.room = new Room(resp.body['name'], resp.body['roomAdmin'], resp.body['enabled'], resp.body['modePrivate']);
          console.log(this.room.getName());

          resp.body['messages'].forEach(element => {
                this.messages.push(new Message(element['messageAuthor'],
                element['messageContent'],
                element['messageDate'],
                element['messageEnabled'],
                element['messageReceiver'],
                element['messageVisible']));
          });

          //this.messages.forEach(console.log);
      });      
    }   
  }

  onSubmit() {

  }
}

// ngOnInit() {
//   this.pusherService.messagesChannel.bind('client-new-message', (message) => {
//     this.messages.push(message);
//   });
// }
