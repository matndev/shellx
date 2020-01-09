import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './modules/authentication/authentication/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private userId: number;
  private userAuth: boolean;

  constructor(private authenticationService: AuthenticationService) {
  }

  ngOnInit() {

    this.authenticationService.authenticatedObs.subscribe(res => {
      res == true ? this.userAuth = true : this.userAuth = false;
    });

    var user = this.authenticationService.getCurrentUserInfos();
    if (user !== null) {
      this.userId = user.id;
    }
  }
}
