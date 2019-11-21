import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './modules/authentication/login/login.component';
import { RegisterComponent } from './modules/authentication/register/register.component';
import { ChatComponent } from './modules/templates/chat/chat.component';
import { AuthGuardService } from './core/authentication/guards/auth-guard.service';
import { LogoTextComponent } from './modules/content/logo-text/logo-text/logo-text.component';

const routes: Routes = [
  // { path: '', component: LogoTextComponent, canActivateChild: [AuthGuardService], 
  //   children: [
  //       { path: 'login',          component: LoginComponent },
  //       { path: 'register',       component: RegisterComponent },
  //       { path: 'chat',           component: ChatComponent },
  //       { path: 'chat/:roomId',   component: ChatComponent }
  //       //{ path: '', redirectTo: '/message', pathMatch: 'full' }
  //   ]
  // }
  { path: '',               redirectTo:'home', pathMatch:'full' },
  { path: 'home',           component: LogoTextComponent, canActivate: [AuthGuardService] },
  { path: 'login',          component: LoginComponent, canActivate: [AuthGuardService] },
  { path: 'register',       component: RegisterComponent, canActivate: [AuthGuardService] },
  { path: 'chat',           component: ChatComponent, canActivate: [AuthGuardService] },
  { path: 'chat/:roomId',   component: ChatComponent, canActivate: [AuthGuardService] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
