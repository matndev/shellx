import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/authentication/user.model';
import { AuthenticationService } from '../../authentication/authentication/authentication.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  private currentRoom: number;
  private userList: User[] = [];
  private userLogged: User;
  private userCount: number;
  private modeSidemenu: number;

  constructor(private auth: AuthenticationService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    // this.userLogged = new User( "Pierrho", "", 1 );
    this.modeSidemenu = 1;
    this.userLogged = this.auth.getCurrentUserInfos();
    this.route.paramMap.subscribe(params => {
      if (params.get('roomId') != undefined)
          this.currentRoom = +params.get('roomId');
    });
    // console.log("Current user infos: "+this.userLogged.toString());
  }

  getCurrentRoom() : number {
    return this.currentRoom;
  }
  setCurrentRoom(currentRoomFromRoomComponent: number) {
    this.currentRoom = currentRoomFromRoomComponent;
  }

  setUserList(userListFromUserListComponent: User[]) {
    this.userList = userListFromUserListComponent;
    this.userCount = this.userList.length;
  }

  swipeMenu() {
    if (this.modeSidemenu === 0) {
      this.modeSidemenu = 1;
    }
    else {
      this.modeSidemenu = 0;
    }
  }
}
