import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private registerForm;

  constructor(
    private formBuilder: FormBuilder
  ) { 
    this.registerForm = this.formBuilder.group({
      email: '',
      password: '',
      passwordMatching: ''
    });
  }

  ngOnInit() {
  }

  onSubmit(formData) {
    console.warn("Register form submitted");
    this.registerForm.reset();
  }

}
