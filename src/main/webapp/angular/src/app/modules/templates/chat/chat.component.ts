import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/authentication/user.model';
import { AuthenticationService } from '../../authentication/authentication/authentication.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ChatService } from './chat.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  private currentRoom: number;
  private nextRoom: number;
  private userList: User[] = [];
  private userLogged: User;
  private userCount: number;
  private modeSidemenu: number;

  private arrCommandRoom: Array<any> = [];
  private arrCommandUserlist: Array<any> = [];
  private arrCommandMessage: Array<any> = [];

  private subChangeRoom: Subscription;

  constructor(private auth: AuthenticationService,
              private chatService: ChatService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    // this.userLogged = new User( "Pierrho", "", 1 );
    this.modeSidemenu = 0;
    this.userLogged = this.auth.getCurrentUserInfos();
    this.route.paramMap.subscribe(params => {
      if (params.get('roomId') != undefined)
          this.currentRoom = +params.get('roomId');
    });
    // console.log("Current user infos: "+this.userLogged.toString());
  }

  ngOnDestroy() {
    console.log("CHAT COMPONENT : ngOnDestroy : unsubscription");
    this.subChangeRoom.unsubscribe();
  }

  getCurrentRoom() : number {
    return this.currentRoom;
  }
  setCurrentRoom(currentRoomFromRoomComponent: number) {
    this.currentRoom = currentRoomFromRoomComponent;
  }

  changeRoom(nextRoomId: number) {
    this.nextRoom = nextRoomId;
    console.log("CHAT COMPONENT : changeRoom func : pre-routing");
    this.subChangeRoom = this.chatService.unsubscriberObs.subscribe(res => {
      if (res == true) {
        console.log("CHAT COMPONENT : changeRoom func : post-routing");
        this.router.navigate(["/chat/"+nextRoomId]);
      }
    });
  }

  setUserList(userListFromUserListComponent: User[]) {
    this.userList = userListFromUserListComponent;
    this.userCount = this.userList.length;
  }

  setCommand(arrCommandFromCommandComponent: Array<any>) {
    console.log("CHAT COMPONENT : arrcommand"+arrCommandFromCommandComponent);
    if (arrCommandFromCommandComponent[0] === "room") {
      this.arrCommandRoom = arrCommandFromCommandComponent;
    }
    else if (arrCommandFromCommandComponent[0] === "userlist") {
      this.arrCommandUserlist = arrCommandFromCommandComponent;
    }
    else if (arrCommandFromCommandComponent[0] === "message") {
      this.arrCommandMessage = arrCommandFromCommandComponent;
    }
    else 
    { }
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
