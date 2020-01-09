import { Injectable } from '@angular/core';
import { Room } from 'src/app/shared/models/content/room.model';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommandService {

  private arrCommand: Array<any> = [];
  public arrCommandSubject = new Subject();

  private regexJoinRoom = /^\/join ([0-9]+)$/;              // join
  private regexCreateRoom = /^\/create ([A-Ba-z0-9]+)$/;    // create
  private regexLeaveRoom = /^\/leave ([0-9]+)$/;            // leave
  private regexInviteUser = /^\/invite ([0-9]+)$/;          // invite

  constructor() { }

  detectCommand(input: string) : boolean {

      if(this.isFormValid(input)) {

        // flush array before inserting new data
        this.arrCommand.splice(0);

          if (this.regexJoinRoom.test(input)) {
              var res = this.regexJoinRoom.exec(input);
              this.arrCommand.push("room");
              this.arrCommand.push("join");
              this.arrCommand.push(+res[1]);
              this.arrCommandSubject.next(this.arrCommand);
              return true;
          }
          else if (this.regexCreateRoom.test(input)) {
              var res = this.regexCreateRoom.exec(input);
              this.arrCommand.push("room");
              this.arrCommand.push("create");
              this.arrCommand.push(res[1]);
              this.arrCommandSubject.next(this.arrCommand);
              return true;         
          }
          else if (this.regexLeaveRoom.test(input)) {
              var res = this.regexLeaveRoom.exec(input);
              this.arrCommand.push("room");
              this.arrCommand.push("leave");
              this.arrCommand.push(res[1]);
              this.arrCommandSubject.next(this.arrCommand);
              return true;         
          }
          else if (this.regexInviteUser.test(input)) {
              var res = this.regexInviteUser.exec(input);
              this.arrCommand.push("user");
              this.arrCommand.push("invite");
              this.arrCommand.push(res[1]);
              this.arrCommandSubject.next(this.arrCommand);
              return true;              
          }
          // NOT A COMMAND
          else {
            this.arrCommand.push("message");
            this.arrCommand.push(""); // if reply, insert reply
            this.arrCommand.push(input);
            this.arrCommandSubject.next(this.arrCommand);
            return true;              
          }

      }
      return false;
  }

  isFormValid(stringToCheck: string) : boolean {
      if (typeof stringToCheck == "string" && stringToCheck != null && stringToCheck != undefined) {
          if (stringToCheck.length < 500) {
              return true
          }
          else {
              return false;
          }
      }
      else {
          return false;
      }
  }

}
