import { Component, OnInit, Input, SimpleChanges, OnChanges, EventEmitter, Output, OnDestroy } from '@angular/core';
import { UserlistService } from './userlist.service';
import { User } from 'src/app/shared/models/authentication/user.model';
import { FormBuilder, Validators } from '@angular/forms';
import { Subject, Subscription } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ChatService } from 'src/app/modules/templates/chat/chat.service';

@Component({
  selector: 'app-userlist',
  templateUrl: './userlist.component.html',
  styleUrls: ['./userlist.component.css']
})
export class UserlistComponent implements OnInit, OnChanges, OnDestroy {

  @Input() currentRoom: number;
  @Input() nextRoom: number;
  @Input() modeSideMenu: string;
  @Input() arrCommandUserlist: Array<any> = [];
  @Output() userListEmitter = new EventEmitter<User[]>();

  private users: User[] = [];
  addUserForm;

  private subUserlist: Subscription;

  constructor(private userlistService: UserlistService,
              private chatService: ChatService,
              private formBuilder: FormBuilder) {
    this.addUserForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  ngOnInit() {
    console.log("USERLIST COMPONENT : Init method");
  }

  ngOnDestroy() {
    console.log("USERLIST COMPONENT : Destroy method");
    this.subUserlist.unsubscribe();
  }  

  ngOnChanges(changes: SimpleChanges) {
    if (changes.currentRoom !== undefined && changes.currentRoom.currentValue != changes.currentRoom.previousValue) {

        // this.users.splice(0,this.users.length);

        this.subUserlist = this.userlistService.subscribeUserlist(changes.currentRoom.currentValue)
        // .pipe(takeUntil(this.unsubscribeSubject))
        .subscribe(data => {
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
   
    if (changes.nextRoom !== undefined && changes.nextRoom.currentValue != changes.nextRoom.previousValue) {
      // this.unsubscribeSubject.next();
      // this.unsubscribeSubject.complete();
      this.subUserlist.unsubscribe();
      this.users.splice(0,this.users.length);
      console.log("USERLIST COMPONENT : flush and unsubscription");
      this.chatService.setUnsubscribersValue("userlist", true);
    }  
  }

  async getUsersByRoomId(id: number) : Promise<any> {
    return await this.userlistService.getUsersByRoomId(id).toPromise();
  }  

  // onSubmit() {
  //   console.log("DEBUG : id room: "+this.currentRoom+", id user: "+this.addUserForm.get("content").value);
  //   this.userlistService.add(this.currentRoom, this.addUserForm.get("content").value);
  // } 
   
}
