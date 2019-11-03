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
        this.userlistService.subscribeUserlist(changes.currentRoom.currentValue).subscribe(data => {
          console.log("data: "+data);
          this.users.push(...data);
          this.users = this.users.sort((a,b) => (a.getUsername() > b.getUsername()) ? 1 : ((b.getUsername() > a.getUsername()) ? -1 : 0));
          this.userListEmitter.emit(this.users);
        });

        const resGetUsers = this.getUsersByRoomId(changes.currentRoom.currentValue);
        resGetUsers.then((result) => {
          this.users.push(...result.body);
          // this.users = this.users.sort(function(a, b) { 
          //   return a.getId() - b.getId();
          // });
          this.users = [ ...new Set(this.users) ];
          // this.users still Array type
          // Or useful method for older versions
          // return names.reduce(function(a,b){if(a.indexOf(b)<0)a.push(b);return a;},[]);
          this.users = this.users.sort((a,b) => (a.getUsername() > b.getUsername()) ? 1 : ((b.getUsername() > a.getUsername()) ? -1 : 0));
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
