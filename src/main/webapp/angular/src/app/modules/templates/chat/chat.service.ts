import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private unsubscribers: Map<string, boolean> = new Map();
  private unsubscriberState = new Subject<boolean>();
  public unsubscriberObs = this.unsubscriberState.asObservable();

  constructor() { }

  ngOnInit() {
    this.unsubscribers.set("messages", false);
    this.unsubscribers.set("userlist", false);
    this.unsubscriberState.next(false);
  } 

  setUnsubscribersValue(component: string, bool: boolean) {
    this.unsubscribers.set(component, bool);
    if (this.unsubscribers.get("messages") == true && this.unsubscribers.get("userlist") == true) {
      this.unsubscriberState.next(true);
    }
  }
  // getUnsubscribersValue(component: string) : boolean {
  //   return this.unsubcribers.get(component);
  // }

  ngOnDestroy() {
    this.unsubscriberState.next();
    this.unsubscriberState.complete();
  }
}
