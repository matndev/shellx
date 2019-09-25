import { Component, OnInit } from '@angular/core';
import { RoomService } from './room.service';
import { AuthenticationService } from '../../authentication/authentication/authentication.service';
import { Message } from 'src/app/shared/models/content/message.model';
import { Room } from 'src/app/shared/models/content/room.model';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/authentication/user.model';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {

  messages: Message[] = []; 
  room: Room = null;
  headersResp: string[];
  sendMessageForm;
  private users: User[] = [];
  private rooms: Room[] = [];

  constructor(
    private roomService: RoomService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.sendMessageForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  // Get first rooms menu by user id with small details (id, name, enabled, private, room admin)
  // Get then informations related to the current room (users, messages)

  ngOnInit() {
    const resGetRooms = this.getRooms();
    resGetRooms.then((result) => {
      result.body.forEach(element => {
        this.rooms.push(new Room(parseInt(element['id']),
                        element['name'],
                        element['roomAdmin'],
                        element['enabled'],
                        element['modePrivate']
                        ));
      });
    })
    .then(() => {
      var idRoom = this.rooms[0].getId();
      console.log(idRoom);
      this.getRoomById(1);
    });
    
    // this.getRoomInformations();
    // this.getUsersRoom(1);
    // this.roomService
    //   .channelSubscription(1)
    //   // .pipe(map(messages => messages.sort(RoomComponent.descendingByPostedAt)))
    //   .subscribe(messages => this.messages = messages); 
  }

  public getRoomById(id: number) {
    this.roomService.getRoom(id).subscribe(data => {
      console.log(data.body);
      //this.room = new Room(null, data.body['name'], data.body['roomAdmin'], data.body['enabled'], data.body['modePrivate']);
    });
  } 



  async getRooms() : Promise<any> {
    return await this.roomService.getRoomsByUserId("1").toPromise();
  }  

  public getUsersRoom(id: number) {
    this.roomService.getUsersByRoomId(id).subscribe(data => {
      data.body.forEach(element => {
            this.users.push(new User(element['username']));
      });
    });
  }

  // public static descendingByPostedAt(message1: Message, message2: Message): number {
  //   return message2.getMessageDate.getTime() - message1.postedAt.getTime();
  // }

  onSubmit() {
    this.createMessage(this.sendMessageForm);
  }

  public createMessage(data: any) : void {
    // A MODIFIER
    //var date = new Date().toJSON().slice(0,10).replace(/-/g,'/');
    var date = new Date();
    let newMessage = new Message( new User("pierrho", "", 1),
                                  data.get("content").value,
                                  date,
                                  true,
                                  null,
                                  true,
                                  this.room.getId());
    this.roomService.save(newMessage);
  }
}

// ngOnInit() {
//   this.pusherService.messagesChannel.bind('client-new-message', (message) => {
//     this.messages.push(message);
//   });
// }
