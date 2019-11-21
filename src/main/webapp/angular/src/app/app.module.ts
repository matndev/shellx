import { BrowserModule, DomSanitizer } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';

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
import { CommandComponent } from './modules/content/command/command/command.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    MessageComponent,
    RoomComponent,
    ChatComponent,
    UserlistComponent,
    LogoTextComponent,
    CommandComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatIconModule
  ],
  providers: [
    CookieService,
    { provide: HTTP_INTERCEPTORS, useClass: CustomHttpInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon('multiusers', sanitizer.bypassSecurityTrustResourceUrl('./assets/icons/material/people-24px.svg'));
    iconRegistry.addSvgIcon('swipe-right', sanitizer.bypassSecurityTrustResourceUrl('./assets/icons/material/arrow_forward_ios-24px.svg'));
  }
}
