import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { passwordMatchingValidator } from '../../../validator/password-matching-validator';
import { User } from 'src/app/shared/models/user.model';
import { AuthenticationService } from '../authentication/authentication.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm;
  user: User;

  constructor(
      private formBuilder: FormBuilder,
      private authenticationService: AuthenticationService,
      private router: Router
  ) { 
      this.loginForm = this.formBuilder.group({
          email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
          password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],
          matchingPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]]
      }, { validators: passwordMatchingValidator });
  }

  ngOnInit() {
  }

  onSubmit() {

    /*console.warn("login submitted.");
    console.warn(this.loginForm.value);

    var userLogin = new User(this.loginForm.get("email").value,
                   this.loginForm.get("password").value,
                   this.loginForm.get("passwordMatching").value);*/
    
    console.log(this.loginForm.get("email").value);
    console.log(this.loginForm.get("password").value);

    var credentials = { email: this.loginForm.get("email").value, // Attention email à la place de username peut créer prob
                    password: this.loginForm.get("password").value};

    this.authenticationService.login(credentials, () => {
      this.router.navigateByUrl('/');
    });

    //this.loginForm.reset();
  }
}