import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/authentication/user.model';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  private currentRoom: number;
  private userList: User[] = [];
  private userLogged: User;

  constructor() { }

  ngOnInit() {
    this.userLogged = new User( "Pierrho", "", 1);
  }

  getCurrentRoom() : number {
    return this.currentRoom;
  }
  setCurrentRoom(currentRoomFromRoomComponent: number) {
    this.currentRoom = currentRoomFromRoomComponent;
  }

  setUserList(userListFromUserListComponent: User[]) {
    this.userList = userListFromUserListComponent;
  }
}
