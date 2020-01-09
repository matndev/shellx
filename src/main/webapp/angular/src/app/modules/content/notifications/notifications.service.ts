import { Injectable, OnInit, OnChanges, OnDestroy } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication/authentication.service';
import { Observable, throwError } from 'rxjs';
import { SocketClientService } from 'src/app/core/websocket/socket-client.service';
import { first, map, catchError } from 'rxjs/operators';
import { Notification } from '../../../shared/models/content/notification.model';
import * as moment from 'moment';
import { HttpResponse, HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    //'Authorization': 'Basic ' + btoa('mail:password')
  }), 
  observe: 'response' as 'body'
};

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  constructor(
    private authenticationService: AuthenticationService,
    private socketClient: SocketClientService,
    private http: HttpClient,
    private router: Router
  ) { }

  public subscribeChannel() : Observable<Notification[]> {
    var id = this.authenticationService.getCurrentUserInfos().getId();
    return this.socketClient
            .onMessage('/topic/notifications/subscribe/'+id)
            .pipe(first(), map(notifications => {
                    var newNotifications: Notification[] = [];
                    if (notifications instanceof Array){
                        notifications.forEach(e => newNotifications.push(new Notification(  e.id, 
                                                                                            e.title, 
                                                                                            e.description, 
                                                                                            e.type, 
                                                                                            e.idUser, 
                                                                                            e.idRoom,
                                                                                            e.dateCreation, 
                                                                                            e.read)));
                        notifications = this.dateManager(notifications);
                    }
                    else {
                        newNotifications.push(new Notification( notifications.id, 
                                                                notifications.title, 
                                                                notifications.description, 
                                                                notifications.type, 
                                                                notifications.idUser,
                                                                notifications.idRoom,
                                                                notifications.dateCreation, 
                                                                notifications.read ));
                        newNotifications = this.dateManager(newNotifications);
                    }
                    return newNotifications;
            }));    
  }

  public getNotificationsByUserId() : Observable<HttpResponse<any>> {
    var id = this.authenticationService.getCurrentUserInfos().getId();
    return this.http.get<HttpResponse<any>>("http://localhost:8086/notifications/get/all/"+id, httpOptions)
    .pipe(
        map(obj => {
          var notificationsArray: Notification[] = [];
          obj.body.forEach(e => 
            notificationsArray.push(new Notification( e.id, 
                                                      e.title, 
                                                      e.description, 
                                                      e.type, 
                                                      e.user, 
                                                      e.room,
                                                      e.dateCreation, 
                                                      e.read ))
          );
          return obj.clone({
            body: notificationsArray
          });
        }),
        catchError(this.handleError.bind(this)) // .bind(this) used to pass the context
    );
  }

  private dateManager(notifications: any) : Notification[] {
    notifications.forEach(element => {
      if (element.notificationDate != null) {
          var utc = moment.utc(element.notificationDate).toDate();
          element.notificationDate = moment(utc).local().format('YYYY-MM-DD HH:mm:ss');
      }
    });
    return notifications;
  } 

  public deleteById(idNotif: number) : Observable<HttpResponse<boolean>> {
      // var obj = { idNotif: idNotif };
      return this.http.post<HttpResponse<any>>("http://localhost:8086/notifications/delete", JSON.stringify(idNotif), httpOptions)
                      .pipe(
                        catchError(this.handleError.bind(this))
                      );
  }
  
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
      if (error.error == "token-expired") {
        this.router.navigateByUrl('/login');
      }
    }
    return throwError(
      'Something bad happened; please try again later.');
  };
    
}
