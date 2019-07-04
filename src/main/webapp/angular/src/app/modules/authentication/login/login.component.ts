import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { passwordMatchingValidator } from '../../../validator/password-matching-validator';
import { ConfigHttpService } from 'src/app/core/http/config-http.service';
import { User } from 'src/app/shared/models/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm;
  configHttpService: ConfigHttpService;
  user: User;

  constructor(
      private formBuilder: FormBuilder 
  ) { 
      this.loginForm = this.formBuilder.group({
          email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
          password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],
          passwordMatching: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]]
      }, { validators: passwordMatchingValidator });
  }

  ngOnInit() {
  }

  onSubmit(formData) {

    console.warn("login submitted.");
    console.warn(this.loginForm.value);

    var userLogin = new User(this.loginForm.get("email"),
                   this.loginForm.get("password"));
    

    this.configHttpService
    .login(userLogin)
    .subscribe(user => this.user);

    //this.loginForm.reset();
  }

}