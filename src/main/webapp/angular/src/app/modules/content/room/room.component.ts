import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges, HostBinding, OnDestroy } from '@angular/core';
import { RoomService } from './room.service';
import { Room } from 'src/app/shared/models/content/room.model';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { CommandService } from '../command/command.service';
import { AuthenticationService } from '../../authentication/authentication/authentication.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit, OnChanges, OnDestroy {
  
  @Input() currentRoom: number;
  @Input() userCount: number;
  @Input() modeSideMenu: string;
  @Input() arrCommandRoom: Array<any> = [];
  @Output() currentRoomEmitter = new EventEmitter<number>();
  @Output() changeRoomEmitter = new EventEmitter<number>();


  room: Room = null;
  headersResp: string[];

  isNewRoomExists: boolean = false;
  // private users: User[] = [];
  private rooms: Room[] = [];

  subscriptionCommandService;


  constructor(
    private authenticationService: AuthenticationService,
    private roomService: RoomService,
    private commandService: CommandService,
    private router: Router
  ) {

    // COMMAND FORM SUBSCRIPTION
    this.subscriptionCommandService = this.commandService.arrCommandSubject.subscribe(command => {
      if (command[0] === "room") {
        if (command[1] === "join") {
          this.roomService.join(command[2], JSON.parse(localStorage.getItem("user")).id).subscribe(result => {
            this.rooms.push(new Room( result.body['id'],
                                      result.body['name'],
                                      result.body['description'],
                                      result.body['roomAdmin'],
                                      result.body['enabled'],
                                      result.body['modePrivate']));
          });
        }
        else if (command[1] === "create") {
          let newRoom = new Room(null, command[2], null, 1, true, false);
          this.roomService.createNewRoom(newRoom).subscribe(result => {
              this.rooms.push(new Room(result.body['id'],
                                        result.body['name'],
                                        result.body['description'],
                                        result.body['roomAdmin'],
                                        result.body['enabled'],
                                        result.body['modePrivate']));
          });           
        }
        else if (command[1] === "leave") {   
          var index = this.rooms.findIndex(x => x.getId()==command[2]);
          console.log("Index dans tableau de la room à supprimer : "+index);
          this.rooms.forEach((x,y) => console.log("Index : "+y+", Room ID : "+x.getId()+", Room Name : "+x.getName()));
          this.roomService.leave(command[2], JSON.parse(localStorage.getItem("user")).id).subscribe(res => {

            if (res.status === 200) {
              this.rooms.splice(index,1);
            }
          });
        }
        else {}
      }
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
      this.currentRoom != undefined ? this.getRoomById(this.currentRoom) : this.getRoomById(this.rooms[0].getId());
    })
    .catch(error => {
      // Executed if the promise result is null
      //console.log(error.message);
    });
  }

  async getRooms() : Promise<any> {
    var id = this.authenticationService.getCurrentUserInfos().id;

    return await new Promise((resolve, reject) => {
      this.roomService.getRoomsByUserId(id).subscribe(res => {
        if (res.body !== null) {
          resolve(res);
        }
        else {
          reject();
        }
      });
    });
  } 

  // async getRooms() : Promise<any> {
  //   var id = this.authenticationService.getCurrentUserInfos().id;
  //   return await this.roomService.getRoomsByUserId(id).toPromise();
  // }   

  public getRoomById(id: number) {
    this.roomService.getRoom(id).subscribe(data => {
      this.room = new Room(data.body['id'], data.body['name'], data.body['description'], data.body['roomAdmin'], data.body['enabled'], data.body['modePrivate']);
    });
  } 
 
  ngOnChanges(changes: SimpleChanges): void {

    if (changes.currentRoom !== undefined && changes.currentRoom.currentValue != changes.currentRoom.previousValue) {
        this.getRoomById(this.currentRoom);
    }

  }

  ngOnDestroy() {
    console.log("ROOM COMPONENT : Destroy method");
    this.subscriptionCommandService.unsubscribe();
  }

  // Transfers new room id to chat comp, message comp, and userlist comp
  // unsubscribe from all socket sources and flush arrays
  changeRoom(idNextRoom: number) {
      console.log("id next room: "+idNextRoom);
      this.changeRoomEmitter.emit(idNextRoom);
  }
}
