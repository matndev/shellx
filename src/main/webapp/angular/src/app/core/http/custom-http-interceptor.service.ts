// Allows to send withCredentials with all requests to server

import { Injectable, OnInit } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class CustomHttpInterceptorService implements HttpInterceptor {

  private XSRF: string;

  constructor(private cookieService: CookieService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (request.method === 'GET' || request.method === 'HEAD') {
        // Allow using of cookies and headers to authenticate in back-end
        request = request.clone({
            withCredentials: true
        });
    }
    else {
        this.XSRF = this.cookieService.get("XSRF-TOKEN");
        request = request.clone({
            withCredentials: true,
            setHeaders: { "XSRF-TOKEN": this.XSRF }
        });
    }

    return next.handle(request);

    // return next.handle(request).pipe(map(event => {
    //   if (event instanceof HttpResponse && shouldBeIntercepted(event)) {
    //       //event = event.clone({ body: resolveReferences(event.body) })
    //       event.status
    //   }         
    //   return event;
    // }));

  }
}
