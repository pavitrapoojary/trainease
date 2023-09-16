import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule, HttpParams } from '@angular/common/http'
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

  getRoleSpecificUsers(role: string): Observable<User[]> {
    const params = new HttpParams().set('role', role);
    return this.http.get<User[]>(`${this.baseUrl}/users`,{params:params});
  }

  getBatchSpecificTrainees(batchId: string): Observable<User[]> {
    const params = new HttpParams().set('batchId', batchId);
    return this.http.get<User[]>(`${this.baseUrl}/users`,{params:params});
  }

  getUserByEmailId(emailId:string):Observable<User>{
    return this.http.get<User>(`${this.baseUrl}/users/${emailId}`)
  }

  addUser(user:User): Observable<User[]> {
    return this.http.post<User[]>(`${this.baseUrl}/users`,user);
  }

  updateUser(user:User):Observable<string>{
    return this.http.put<string>(`${this.baseUrl}/users`,user);
  }

  deleteUserByEmailId(emailId:string):Observable<string>{
    return this.http.delete(`${this.baseUrl}/users/${emailId}`,{responseType:'text'});
  }
}
