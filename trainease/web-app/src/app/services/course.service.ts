import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Course } from '../models/course.model';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http:HttpClient) { }

  getCoursesByBatchId(batchId:string):Observable<Course[]>{
    return this.http.get<Course[]>(`${this.baseUrl}/${batchId}/courses`);
  }
}
