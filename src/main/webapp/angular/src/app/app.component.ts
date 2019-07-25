import { Component } from '@angular/core';
import { AuthenticationService } from './modules/authentication/authentication/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(authenticationService: AuthenticationService) {
      /*authenticationService.authenticate(undefined, undefined);*/
      console.log(authenticationService.isAuthenticated());
  }

  /*authenticated() {
    return authenticationService.authenticated;
  }*/
}
