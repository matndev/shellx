/***
 * Extracted from Dimitri's tutorials
 * 
 * https://dimitr.im/websockets-angular
 * https://github.com/g00glen00b/spring-boot-angular-websockets/tree/master/angular-websockets-client
 * 
 */

import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Message, StompSubscription, CompatClient, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { filter, first, switchMap } from 'rxjs/operators';
import { SERVER_HTTPS_URL } from 'src/environments/environment';

export enum SocketClientState {
  ATTEMPTING, CONNECTED
}

@Injectable({
  providedIn: 'root'
})
export class SocketClientService implements OnDestroy {

  private client: CompatClient;
  private state: BehaviorSubject<SocketClientState>;
  private url = SERVER_HTTPS_URL; // Not supported in SockJS constructor 

  constructor() { 
    console.log("Connection Websocket beginning");
    this.client = Stomp.over(function() { return new SockJS("https://localhost:8443/wss")}); // this.url or any attribute not supported
    this.state = new BehaviorSubject<SocketClientState>(SocketClientState.ATTEMPTING);
    this.client.connect({}, () => {
      console.log("connexion established");
      this.state.next(SocketClientState.CONNECTED);
    });
  }

  private connect() : Observable<CompatClient> {
    return new Observable<CompatClient>(observer => {
      this.state.pipe(filter(state => state === SocketClientState.CONNECTED)).subscribe(() => {
        observer.next(this.client);
      });
    });
  }

  ngOnDestroy() {
    console.log("Websocket Disconnection");
    this.disconnect();
  }

  public disconnect() {
    this.connect().pipe(first()).subscribe(client => client.disconnect(null));
  }

  public onMessage(topic: string, handler = SocketClientService.jsonHandler) : Observable<any> {
    // Get the very first connection, complete obs, then returning a new observable with switchMap
    return this.connect().pipe(first(), switchMap(client => {
      return new Observable<any>(observer => {
        const subscription : StompSubscription = client.subscribe(topic, message => {
            observer.next(handler(message));
        });
        return () => {
          console.log("Unsubscribe from socket-client service");
          client.unsubscribe(subscription .id);
        }
      });
    }));
  }

  public onPlainMessage(topic: string): Observable<string> {
    return this.onMessage(topic, SocketClientService.textHandler);
  }

  private static jsonHandler(message: Message): any {
    return JSON.parse(message.body);
  }
  
  private static textHandler(message: Message): string {
    return message.body;
  }

  public send(topic: string, payload: any): void {
    this.connect()
      .pipe(first())
      .subscribe(client => client.send(topic, {}, JSON.stringify(payload)));
  }
}
