// Allows to send withCredentials with all requests to server

import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CustomHttpInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // Allow using of cookies and headers to authenticate in back-end
    request = request.clone({
        withCredentials: true
    });

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
