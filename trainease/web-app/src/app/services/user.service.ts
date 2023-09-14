import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getAllUsers(role?: string, batchId?: string): Observable<User[]> {
    const params = {
      role: role || '',
      batchId: batchId || ''
    };
    return this.http.get<User[]>(`${this.baseUrl}/users`);
  }
}
