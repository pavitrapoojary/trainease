import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CourseProgress } from '../models/course-progress.model';

@Injectable({
  providedIn: 'root'
})
export class ProgressService {
  private baseUrl='http://localhost:8080';

  constructor(private http:HttpClient) { }

  getCoursesProgressByTraineeEmailId(emailId:string):Observable<CourseProgress[]>{
    return this.http.get<CourseProgress[]>(`${this.baseUrl}/${emailId}/progress`);
  }
}
