import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CourseWiseStatus } from '../models/course-wise-status.model';
import { environment } from 'src/environments/environment';
import { BatchProgress } from '../models/batch-progress.model';


@Injectable({
  providedIn: 'root'
})
export class ReportService {

  baseUrl = environment.baseUrl;
  isProduction = environment.production;

  constructor(private http: HttpClient) { }

  getTotalCoursesInBatch(batchId: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/report/totalCourses/${batchId}`);
  }

  getTotalTraineesInBatch(batchId: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/report/totalTrainees/${batchId}`);
  }

  getCourseIdWiseStatusCountForAdmin(courseId: string): Observable<CourseWiseStatus> {
    return this.http.get<CourseWiseStatus>(`${this.baseUrl}/report/status/admin/${courseId}`);
  }

  getFeedbackListByCourseId(courseId: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/report/feedback/${courseId}`);
  }

  getTraineeBatchId(emailId: string): Observable<string> {
    const options = { responseType: 'text' as 'json' };
    return this.http.get<string>(`${this.baseUrl}/report/batchId/${emailId}`, options);
  }


  getTotalCoursesStatusCountForTrainee(emailId: string): Observable<CourseWiseStatus> {
    return this.http.get<CourseWiseStatus>(`${this.baseUrl}/report/status/${emailId}`);
  }

  getBatchProgress(batchId: string): Observable<BatchProgress[]> {
    return this.http.get<BatchProgress[]>(`${this.baseUrl}/report/batch/${batchId}/progress`);
  }


}
