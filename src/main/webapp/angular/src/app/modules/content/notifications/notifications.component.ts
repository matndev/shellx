import { Component, OnInit, OnChanges, OnDestroy, Input } from '@angular/core';
import { NotificationsService } from './notifications.service';
import { Subscription } from 'rxjs';
import { Notification } from '../../../shared/models/content/notification.model';
import { UserlistService } from '../user/userlist/userlist.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit, OnChanges, OnDestroy {

  @Input() modeSideMenu: string;

  private subNotifications: Subscription;

  private notifications: Notification[] = [];

  constructor(
    private notificationsService: NotificationsService,
    private userlistService: UserlistService
  ) { }

  ngOnInit() {
    this.subNotifications = this.notificationsService.subscribeChannel().subscribe(result => {
      this.notifications.push(...result);
      console.log("notifications: "+this.notifications);
      // this.notifications.forEach(e => console.log(e.getDescription()));
    });
    const resGetNotifications = this.getNotificationsByUserId();
    resGetNotifications.then((result) => {
      this.notifications.push(...result.body);
      this.notifications = [ ...new Set(this.notifications) ];
      this.notifications = this.notifications.sort((a,b) => (a.getDateCreation() > b.getDateCreation()) ? 1 : ((b.getDateCreation() > a.getDateCreation()) ? -1 : 0));        
    });   
  }

  ngOnChanges() {

  }

  ngOnDestroy() {
    console.log("NOTIFICATIONS COMPONENT : ngOnDestroy : unsubscription");
    if (this.subNotifications !== undefined) {
      this.subNotifications.unsubscribe();
    }
  }

  async getNotificationsByUserId() : Promise<any> {
    return await this.notificationsService.getNotificationsByUserId().toPromise();
  }

  public notifOnClick(event: any) {
    var arrIds = (event.target.value).split(',');
    this.userlistService.add(arrIds[0], arrIds[1]);
  }

  public deleteNotif(event: any) {
    this.notificationsService.deleteById(event.currentTarget.value).subscribe(res => {
        if (res.body == true) {
          console.log("Notification deleted");
          var index = this.notifications.findIndex(x => x.getId()==event.currentTarget.value);
          this.notifications.splice(index,1);
        }
    });
  }

}
