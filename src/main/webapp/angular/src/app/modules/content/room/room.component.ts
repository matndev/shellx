import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges, HostBinding } from '@angular/core';
import { RoomService } from './room.service';
import { Room } from 'src/app/shared/models/content/room.model';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit, OnChanges {
  
  @Input() currentRoom: number;
  @Input() userCount: number;
  @Input() modeSideMenu: string;
  @Input() arrCommandRoom: Array<any> = [];
  @Output() currentRoomEmitter = new EventEmitter<number>();
  @Output() changeRoomEmitter = new EventEmitter<number>();


  room: Room = null;
  headersResp: string[];

  createRoomForm;
  isNewRoomExists: boolean = false;
  // private users: User[] = [];
  private rooms: Room[] = [];

  constructor(
    private roomService: RoomService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.createRoomForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  // Get first rooms menu by user id with small details (id, name, enabled, private, room admin)
  // Get then informations related to the current room (users, messages)

  ngOnInit() {
    console.log("ROOM COMPONENT : INIT : current room value : "+this.currentRoom);
    // Get rooms
    const resGetRooms = this.getRooms();
    resGetRooms.then((result) => {
      result.body.forEach(element => {
        this.rooms.push(new Room(parseInt(element['id']),
                        element['name'],
                        element['description'],
                        element['roomAdmin'],
                        element['enabled'],
                        element['modePrivate']
                        ));
      });
    })
    .then(() => {
      // Sort rooms by ID
      this.rooms = this.rooms.sort((first, second) => {
          // console.log("first : "+first.getName()+", second: "+second.getName());
          if (first.getId() > second.getId()) {
              return 1;
          }
          else {
              return -1;
          }
      }); 
    })
    .then(() => {
      // Send back first room ID
      if (this.currentRoom == undefined)
          this.currentRoomEmitter.emit(this.rooms[0].getId());
    })   
    .then(() => {
      // Get current room info
      // var idRoom = this.rooms[0].getId();
      this.currentRoom != undefined ? this.getRoomById(this.currentRoom) : this.getRoomById(this.rooms[0].getId());
    });
  }

  async getRooms() : Promise<any> {
    return await this.roomService.getRoomsByUserId("1").toPromise();
  } 

  public getRoomById(id: number) {
    this.roomService.getRoom(id).subscribe(data => {
      this.room = new Room(data.body['id'], data.body['name'], data.body['description'], data.body['roomAdmin'], data.body['enabled'], data.body['modePrivate']);
    });
  } 
 
  ngOnChanges(changes: SimpleChanges): void {
    // console.log("ROOM COMPONENT : ngOnChanges"); changes.arrCommandRoom
    if (changes.arrCommandRoom !== undefined && changes.arrCommandRoom.currentValue.length != 0 && changes.arrCommandRoom.currentValue != changes.arrCommandRoom.previousValue) {
          console.log("changes 2: "+changes.arrCommandRoom.currentValue[1]);
          if (changes.arrCommandRoom.currentValue[1] === "join") {
                  this.roomService.join(this.arrCommandRoom[2], JSON.parse(localStorage.getItem("user")).id).subscribe(result => {
                  this.rooms.push(new Room(result.body['id'],
                                              result.body['name'],
                                              result.body['description'],
                                              result.body['roomAdmin'],
                                              result.body['enabled'],
                                              result.body['modePrivate']));
                  });
          }
          else if (changes.arrCommandRoom.currentValue[1] === "create") {
              // if (this.arrCommandRoom[2] instanceof Room) {
                  let newRoom = new Room(null, this.arrCommandRoom[2], null, 1, true, false);
                  this.roomService.createNewRoom(newRoom).subscribe(result => {
                      this.rooms.push(new Room(result.body['id'],
                                                result.body['name'],
                                                result.body['description'],
                                                result.body['roomAdmin'],
                                                result.body['enabled'],
                                                result.body['modePrivate']));
                  }); 
              // }        
          }
          else if (changes.arrCommandRoom.currentValue[1] === "leave") {
                  //this.roomService.leave(this.arrCommandRoom[2], JSON.parse(localStorage.getItem("user")).id).subscribe(result => {
                  var index = this.rooms.findIndex(x => x.getId()==this.arrCommandRoom[2]);
                  console.log("Index dans tableau de la room à supprimer : "+index);
                  this.rooms.forEach((x,y) => console.log("Index : "+y+", Room ID : "+x.getId()+", Room Name : "+x.getName()));
                  //});
          }          
          else {}
    }
    else {} // console.log("DEBUGGGG erreur");

    if (changes.currentRoom !== undefined && changes.currentRoom.currentValue != changes.currentRoom.previousValue) {
        this.getRoomById(this.currentRoom);
    }

  }

  // Transfers new room id to chat comp, message comp, and userlist comp
  // unsubscribe from all socket sources and flush arrays
  changeRoom(idNextRoom: number) {
      console.log("id next room: "+idNextRoom);
      this.changeRoomEmitter.emit(idNextRoom);
  }

  // public getUsersRoom(id: number) {
  //   this.roomService.getUsersByRoomId(id).subscribe(data => {
  //     data.body.forEach(element => {
  //           this.users.push(new User(element['username']));
  //     });
  //   });
  // }

  // public static descendingByPostedAt(message1: Message, message2: Message): number {
  //   return message2.getMessageDate.getTime() - message1.postedAt.getTime();
  // }

  // onSubmit() {
  //   let newRoom = new Room(null, this.createRoomForm.get("content").value, null, 1, true, false, null, null);
  //   this.roomService.createNewRoom(newRoom).subscribe(result => {
  //       this.rooms.push(new Room(result.body['id'],
  //                                 result.body['name'],
  //                                 result.body['description'],
  //                                 result.body['roomAdmin'],
  //                                 result.body['enabled'],
  //                                 result.body['modePrivate']));
  //   });
  //   // this.roomService.createNewRoom(newRoom);
  // }

  // public createMessage(data: any) : void {
  //   // A MODIFIER
  //   //var date = new Date().toJSON().slice(0,10).replace(/-/g,'/');
  //   // var date = new Date();
  //   let newMessage = new Message( new User("pierrho", "", 1),
  //                                 data.get("content").value,
  //                                 null,
  //                                 true,
  //                                 null,
  //                                 true,
  //                                 this.room.getId());
  //   this.roomService.saveNewMessage(newMessage);
  // }
}

// ngOnInit() {
//   this.pusherService.messagesChannel.bind('client-new-message', (message) => {
//     this.messages.push(message);
//   });
// }
