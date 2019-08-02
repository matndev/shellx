import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Message } from 'src/app/shared/models/content/message.model';
import { Room } from 'src/app/shared/models/content/room.model';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    //'Authorization': 'Basic ' + btoa('eboyfr@gmail.com:12345678')
  }), 
  observe: 'response' as 'body'
};

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(
    private http: HttpClient
  ) { }

  getRoom(id: string): Observable<HttpResponse<Room>> {
    return this.http.get<HttpResponse<Room>>("http://localhost:8086/rooms/"+id, httpOptions);
  }

  getRoomWithMessages(id: string): Observable<HttpResponse<Message[]>> {
    return this.http.get<HttpResponse<Message[]>>("http://localhost:8086/rooms/c/"+id, httpOptions);
  }
}
