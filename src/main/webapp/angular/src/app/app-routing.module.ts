import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './modules/authentication/login/login.component';
import { RegisterComponent } from './modules/authentication/register/register.component';
import { ChatComponent } from './modules/templates/chat/chat.component';

const routes: Routes = [
  { path: 'login',      component: LoginComponent },
  { path: 'register',   component: RegisterComponent },
  // { path: 'room',       component: RoomComponent },
  { path: 'chat',       component: ChatComponent },
  //{ path: '', redirectTo: '/message', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
