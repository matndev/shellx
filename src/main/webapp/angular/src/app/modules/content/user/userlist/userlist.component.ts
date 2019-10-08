import { Component, OnInit, Input, SimpleChanges, OnChanges, EventEmitter, Output } from '@angular/core';
import { UserlistService } from './userlist.service';
import { User } from 'src/app/shared/models/authentication/user.model';

@Component({
  selector: 'app-userlist',
  templateUrl: './userlist.component.html',
  styleUrls: ['./userlist.component.css']
})
export class UserlistComponent implements OnInit, OnChanges {

  @Input() currentRoom: number;
  @Output() userListEmitter = new EventEmitter<User[]>();
  private users: User[] = [];

  constructor(private userlistService: UserlistService) { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.currentRoom.currentValue != null) {
        const resGetUsers = this.getUsersByRoomId(changes.currentRoom.currentValue);
        resGetUsers.then((result) => {
          result.body.forEach(element => {
            this.users.push(new User(element['username'], null, element['id']));
          });
        })
        .then(() => {
          this.userListEmitter.emit(this.users);
        });
    }
  }  
  
  // async getUsersByRoomId(id: number) {
  //   this.userlistService.getUsersByRoomId(id).subscribe(data => {
  //     data.body.forEach(element => {
  //           this.users.push(new User(element['username'], null, element['id']));
  //     });
  //   });
  // }

  async getUsersByRoomId(id: number) : Promise<any> {
    return await this.userlistService.getUsersByRoomId(id).toPromise();
  }

}
