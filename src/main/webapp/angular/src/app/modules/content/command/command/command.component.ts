import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/shared/models/authentication/user.model';
import { CommandService } from '../command.service';

@Component({
  selector: 'app-command',
  templateUrl: './command.component.html',
  styleUrls: ['./command.component.css'],
  host: { 'class' : 'row shx-chat-form-frame' }
})
export class CommandComponent implements OnInit {

  // @Input() currentRoom: number;
  @Input() userLogged: User;
  @Output() commandEmitter = new EventEmitter<Array<any>>();
  // @Input() userList: User[] = [];
  // messages: Message[] = []; 
  // previousMessages: Message[] = [];
  // headersResp: string[];
  sendCommandForm;

  constructor(
    private commandService: CommandService,
    private formBuilder: FormBuilder,
    private router: Router    
  ) { 
    this.sendCommandForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(100)]]
    });    
  }

  ngOnInit() {
  }

  onSubmit() {
    if (this.userLogged != null) {
        var arrCommand = this.commandService.detectCommand(this.sendCommandForm.get("content").value);
        this.sendCommandForm.reset();
        if (arrCommand != null && arrCommand != undefined) {
          console.log("arrCommand value : "+arrCommand[0]+" / "+arrCommand[1]+" / "+arrCommand[2]);
          this.commandEmitter.emit(arrCommand);
          // arrCommand.splice(0);
        }
        else {
          console.log("COMMAND COMPONENT : This command is not valid");
        }
    } 
  }

}
