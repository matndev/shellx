import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication/authentication.service';
import { User } from 'src/app/shared/models/authentication/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  host: { 'class': 'row flex-fill' }
})
export class ProfileComponent implements OnInit {

  private user: User;

  constructor(
    private authService: AuthenticationService
  ) { }

  ngOnInit() {
    this.user = this.authService.getCurrentUserInfos();
  }

}
