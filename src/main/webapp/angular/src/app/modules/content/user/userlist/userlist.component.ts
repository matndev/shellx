import { Component, OnInit, Input, SimpleChanges, OnChanges, EventEmitter, Output } from '@angular/core';
import { UserlistService } from './userlist.service';
import { User } from 'src/app/shared/models/authentication/user.model';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-userlist',
  templateUrl: './userlist.component.html',
  styleUrls: ['./userlist.component.css']
})
export class UserlistComponent implements OnInit, OnChanges {

  @Input() currentRoom: number;
  @Output() userListEmitter = new EventEmitter<User[]>();
  private users: User[] = [];
  addUserForm;

  constructor(private userlistService: UserlistService,
              private formBuilder: FormBuilder) {
    this.addUserForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.currentRoom !== undefined && changes.currentRoom.currentValue != changes.currentRoom.previousValue) {
        // this.userlistService.subscribeUserlist(changes.currentRoom.currentValue).subscribe(data => {
        //   data.forEach(user => this.users.push(user));
        // });
        const resGetUsers = this.getUsersByRoomId(changes.currentRoom.currentValue);
        resGetUsers.then((result) => {
          this.users = result;
        })
        .then(() => {
          this.userListEmitter.emit(this.users);
        });
    }
  }

  async getUsersByRoomId(id: number) : Promise<any> {
    return await this.userlistService.getUsersByRoomId(id).toPromise();
  }  

  onSubmit() {
    // if (this.userLogged != null) {
    //     this.userlistService.saveNewMessage(newMessage);
    // } 
    console.log("DEBUG : id room: "+this.currentRoom+", id user: "+this.addUserForm.get("content").value);
    this.userlistService.add(this.currentRoom, this.addUserForm.get("content").value);
  }  
  
  // async getUsersByRoomId(id: number) {
  //   this.userlistService.getUsersByRoomId(id).subscribe(data => {
  //     data.body.forEach(element => {
  //           this.users.push(new User(element['username'], null, element['id']));
  //     });
  //   });
  // }
}
