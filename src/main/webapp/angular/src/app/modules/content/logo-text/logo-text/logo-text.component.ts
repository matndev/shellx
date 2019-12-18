import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-logo-text',
  templateUrl: './logo-text.component.html',
  styleUrls: ['./logo-text.component.css'],
  host: { 'class' : 'row flex-fill' }
})
export class LogoTextComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
