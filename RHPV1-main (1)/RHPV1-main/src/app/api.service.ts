import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8070/api/v1';

  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    const loginData = { email, password };
    return this.http.post<any>(`${this.baseUrl}/auth/login`, loginData);
  }

    // Method to create a new request
    createRequest(request: Request): Observable<Request> {
      return this.http.post<Request>(`${this.baseUrl}/Send-req`, request);
    }
  
    // Method to get all requests for the current user
    getMyRequests(): Observable<Request[]> {
      return this.http.get<Request[]>(`${this.baseUrl}/my-requests`);
    }
}
