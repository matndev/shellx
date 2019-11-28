import { Injectable } from '@angular/core';
import { Room } from 'src/app/shared/models/content/room.model';

@Injectable({
  providedIn: 'root'
})
export class CommandService {

  private arrCommand: Array<any> = [];

  private regexJoinRoom = /^\/join ([0-9]+)$/;    // join
  private regexCreateRoom = /^\/create ([A-Ba-z0-9]+)$/;  // create

  constructor() { }

  detectCommand(input: string) : Array<any> {

      if(this.isFormValid(input)) {

          if (this.regexJoinRoom.test(input)) {
              var res = this.regexJoinRoom.exec(input);
              this.arrCommand.push("room");
              this.arrCommand.push("join");
              this.arrCommand.push(+res[1]);
              return this.arrCommand;
          }
          else if (this.regexCreateRoom.test(input)) {
              var res = this.regexCreateRoom.exec(input);
              this.arrCommand.push("room");
              this.arrCommand.push("create");
              this.arrCommand.push(res[1]);
              return this.arrCommand;            
          }
          else {
          }

      }
      return null;
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
