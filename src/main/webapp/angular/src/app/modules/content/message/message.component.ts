import { Component, OnInit, Input, OnChanges, SimpleChanges, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { MessageService } from './message.service';
import { User } from 'src/app/shared/models/authentication/user.model';
import { Message } from 'src/app/shared/models/content/message.model';
import * as moment from 'moment';
import { ChatService } from '../../templates/chat/chat.service';
import { CommandService } from '../command/command.service';


@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css'],
  host: { 'class': 'row d-flex flex-fill shx-messages-frame__content' }
})
export class MessageComponent implements OnInit, OnChanges, OnDestroy {

  @Input() currentRoom: number;
  @Input() nextRoom: number;
  @Input() userLogged: User;
  @Input() userList: User[] = [];
  @Input() arrCommandMessage: Array<any> = [];

  messages: Message[] = [];
  headersResp: string[];
  sendMessageForm;
  subscriptionCommandService;

  private subMessages: Subscription;


  constructor(
      private messageService: MessageService,
      private chatService: ChatService,
      private commandService: CommandService,
      private router: Router
  ) {}

  ngOnInit() {
    console.log("MESSAGE COMPONENT : Init method");

    // COMMAND FORM SUBSCRIPTION
    // If command from CommandService is a new Message
    this.subscriptionCommandService = this.commandService.arrCommandSubject.subscribe(command => {
      if (command[0] === "message") {
          this.send(command[2]);
      }
    });
  }

  ngOnDestroy() {
    console.log("MESSAGE COMPONENT : Destroy method");
    if (this.subMessages !== undefined) {
      this.subMessages.unsubscribe();
    }
    if (this.subscriptionCommandService !== undefined) {
      this.subscriptionCommandService.unsubscribe();
    }
  }

  ngOnChanges(changes: SimpleChanges) {

      if (changes.currentRoom !== undefined && changes.currentRoom.currentValue != changes.currentRoom.previousValue) {
            // Subscribe to new websocket messages
            // Sort by Date
            this.subMessages = this.messageService.subscribeChannel(changes.currentRoom.currentValue)
            .subscribe(data => { 
              this.messages.push(...data);
              this.messages = this.messages.sort((a,b) => (a.getMessageDate() > b.getMessageDate()) ? 1 : ((b.getMessageDate() > a.getMessageDate()) ? -1 : 0));
            });
            // Get initial data (messages)
            this.messageService.getMessagesHistory(changes.currentRoom.currentValue).subscribe(data => {
              data.body.map(element => this.messages.push(element));
              this.messages = [ ...new Set(this.messages) ];
              this.messages = this.messages.sort((a,b) => (a.getMessageDate() > b.getMessageDate()) ? 1 : ((b.getMessageDate() > a.getMessageDate()) ? -1 : 0));        
            });
      }
      
      if (changes.nextRoom !== undefined && changes.nextRoom.currentValue != changes.nextRoom.previousValue) {
            // Alternative to ngOnDestroy : cleaning data before redirection
            if (this.subMessages !== undefined) {
              this.subMessages.unsubscribe();
            }
            this.messages.splice(0,this.messages.length);
            console.log("MESSAGE COMPONENT : flush and unsubscription");
            this.chatService.setUnsubscribersValue("messages", true);
      }
  }

  send(content: string) {
    if (this.userLogged != null) {
        // If message not a response to a specific user in the room let field "Receiver" empty
        let dateUTC = moment.utc().format("YYYY-MM-DDTHH:mm:ssZ");
        let newMessage = new Message( this.userLogged.getId(),
                                      content,
                                      dateUTC,
                                      true,
                                      null,
                                      true,
                                      this.currentRoom);
        this.messageService.saveNewMessage(newMessage);
    } 
  }

  getAuthorUsername(id: number) : string {
    if (this.userList.some(e => e.getId() === id)) {
      return this.userList.find(e => e.getId() === id).getUsername();
    }
    else {
      return "anonymous";
    }
  }

  getReceiverUsername(id: number) : string {
    if (this.userList.some(e => e.getId() === id)) {
      return this.userList.find(e => e.getId() === id).getUsername();
    }
    else {
      return "anonymous";
    }    
  } 

}
