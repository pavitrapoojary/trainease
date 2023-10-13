import { TestBed } from '@angular/core/testing';

import { ProgressService } from './progress.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CourseProgress } from '../models/course-progress.model';
import { environment } from 'src/environments/environment';

describe('ProgressService', () => {
  let service: ProgressService;
  let httpTestingController : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers:[ProgressService]
    });
    service = TestBed.inject(ProgressService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created Progress service', () => {
    expect(service).toBeTruthy();
  });


  it('should get course progress by trainee email id', () => {
    const emailId = 'email@test.com';
    let mockCourseProgress : CourseProgress[] = [];

    service.getCoursesProgressByTraineeEmailId(emailId).subscribe((courseProgressResult) => {
      expect(courseProgressResult).toEqual(mockCourseProgress);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/${emailId}/progress`);
    expect(req.request.method).toEqual('GET');
    req.flush(mockCourseProgress);
  });


  afterEach(() => {
    httpTestingController.verify();
  });


});
