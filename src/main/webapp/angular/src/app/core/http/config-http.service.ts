import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    //'Authorization': 'Basic Auth'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ConfigHttpService {
  
  http: HttpClient;

  constructor(private httpParam: HttpClient) { 
    this.http = httpParam;
  }

  getHttp() : HttpClient {
    return this.http;
  }
}
