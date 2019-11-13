import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './modules/authentication/login/login.component';
import { RegisterComponent } from './modules/authentication/register/register.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MessageComponent } from './modules/content/message/message.component';
import { RoomComponent } from './modules/content/room/room.component';
import { CookieService } from 'ngx-cookie-service';
import { CustomHttpInterceptorService } from './core/http/custom-http-interceptor.service';
import { ChatComponent } from './modules/templates/chat/chat.component';
import { UserlistComponent } from './modules/content/user/userlist/userlist.component';
import { LogoTextComponent } from './modules/content/logo-text/logo-text/logo-text.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    MessageComponent,
    RoomComponent,
    ChatComponent,
    UserlistComponent,
    LogoTextComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    CookieService,
    { provide: HTTP_INTERCEPTORS, useClass: CustomHttpInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
