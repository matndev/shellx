import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { passwordMatchingValidator } from '../../../validator/password-matching-validator';
import { User } from 'src/app/shared/models/user.model';
import { LoginService } from './login.service';

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
      private loginService: LoginService
  ) { 
      this.loginForm = this.formBuilder.group({
          email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
          password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],
          passwordMatching: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]]
      }, { validators: passwordMatchingValidator });
  }

  ngOnInit() {
  }

  onSubmit() {

    console.warn("login submitted.");
    console.warn(this.loginForm.value);

    var userLogin = new User(this.loginForm.get("email").value,
                   this.loginForm.get("password").value,
                   this.loginForm.get("passwordMatching").value);
    
    console.log(userLogin.getEmail());
    console.log(userLogin.getPassword());

    this.loginService.login(userLogin)
    .subscribe((data: User) => this.user = data);

    //this.loginForm.reset();
  }
}