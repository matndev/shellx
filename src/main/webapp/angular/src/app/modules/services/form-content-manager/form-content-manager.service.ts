import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FormContentManagerService {

  private command: string;
  private payload: string;

  private currentRoom: number;
  private currentUser: number;

  constructor() { }

  getFormData(input: string, currentRoom: number, currentUser: number) : Observable<boolean> {
      
      this.currentRoom = currentRoom;
      this.currentUser = currentUser;

      if(this.isFormValid(input)) {

          if (input.startsWith("/add ")) {
              return this.addUser(input);
          }
          else if (input.startsWith("/join ")) {
              return this.joinRoom(input);
          }
          else {
              return this.newMessage(input);
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

  addUser(input: string) : boolean {
      // var regex = /^\/add ([0-9]+)$/;
      // var tab = regex.exec(input);
      // if (tab != null) {
      //     tab[1]
      //     return true;
      // }
      // else {
      //     return false;
      // }
      return true;
  }

  joinRoom(input: string) : boolean {

      var regex = /^\/join ([A-Ba-z0-9]+)$/;
      var tab = regex.exec(input);
      if (tab != null) {
          tab[1]
          return true;
      }
      else {
          return false;
      }

  }

  newMessage(input: string) : boolean {

  }

}
