import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, Route, CanActivateChild } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/modules/authentication/authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

    constructor(private _authService: AuthenticationService, private _router: Router) {
    }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
      
        console.log(state.url);

        if (this._authService.isAuthenticated() && state.url != "/login" && state.url != "/register") {
          return true;
        }
        else if (this._authService.isAuthenticated() && (state.url == "/login" || state.url == "/register")) {
          this._router.navigate(['/chat']);
          return false;    
        }
        else if (!this._authService.isAuthenticated() && (state.url == "/login" || state.url == "/home" || state.url == "/register")) {
          return true;    
        }    
        else {
          this._router.navigate(['/login']);
          return false;
        }
    }
}

    // this will be passed from the route config
    // on the data property

    // const expectedRole = next.data.expectedRole;
    // const token = localStorage.getItem('token');    // decode the token to get its payload
    
    // const tokenPayload = decode(token);
    // if (
    //   !this._authService.isAuthenticated() || 
    //   tokenPayload.role !== expectedRole
    // ) {
    //   this._router.navigate(['login']);
    //   return false;
    // }