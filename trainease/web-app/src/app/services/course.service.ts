import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Course } from '../models/course.model';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class CourseService {
  baseUrl = environment.baseUrl;
  isProduction = environment.production;

  constructor(private http: HttpClient) { }

  getCoursesByBatchId(batchId: string): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.baseUrl}/${batchId}/courses`);
  }

  createCourse(course: Course): Observable<Course> {
    return this.http.post<Course>(`${this.baseUrl}/courses`, course);
  }

  uploadExcelFile(file: File): Observable<Course[]> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<Course[]>(`${this.baseUrl}/courses/saveExcel`, formData);
  }

  updateCourse(course: Course): Observable<Course> {
    return this.http.put<Course>(`${this.baseUrl}/courses`, course);
  }
}
