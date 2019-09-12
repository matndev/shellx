import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { passwordMatchingValidator } from 'src/app/validator/password-matching-validator';
import { AuthenticationService } from '../authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private registerForm;

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router
  ) { 
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
      username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],
      matchingPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]]
    }, { validators: passwordMatchingValidator });
  }

  ngOnInit() {
  }

  onSubmit(formData) {
    console.warn("Register form submitted");
    console.log(formData);

    this.authenticationService.register(formData).subscribe(resp => {
        this.router.navigateByUrl('/');
    });
    //this.registerForm.reset();
  }

}
