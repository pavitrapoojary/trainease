import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl = environment.baseUrl;
  isProduction = environment.production;

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/users`);
  }

  getRoleSpecificUsers(role: string): Observable<User[]> {
    const params = new HttpParams().set('role', role);
    return this.http.get<User[]>(`${this.baseUrl}/users`, { params: params });
  }

  getBatchSpecificTrainees(batchId: string): Observable<User[]> {
    const params = new HttpParams().set('batchId', batchId);
    return this.http.get<User[]>(`${this.baseUrl}/users`, { params: params });
  }

  getUserByEmailId(emailId: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/users/${emailId}`)
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/users`, user);
  }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/users`, user);
  }

  deleteUserByEmailId(emailId: string): Observable<string> {
    return this.http.delete(`${this.baseUrl}/users/${emailId}`, { responseType: 'text' });
  }

  uploadExcelFile(file: File): Observable<User[]> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<User[]>(`${this.baseUrl}/users/saveExcel`, formData);
  }

  getUserRoleAndBatch(emailId: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/users/data/${emailId}`);
  }

  getUserProfile(emailId: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/users/profile/${emailId}`);
  }
}
