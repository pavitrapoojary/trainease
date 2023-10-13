import { TestBed } from '@angular/core/testing';

import { CourseService } from './course.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Course } from '../models/course.model';
import { environment } from 'src/environments/environment';
import { Batch } from '../models/batch.model';

describe('CourseService', () => {
  let service: CourseService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CourseService]
    });

    service = TestBed.inject(CourseService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created Course Service', () => {
    expect(service).toBeTruthy();
  });


  it('should get courses by batch id', () => {
    const batchId = 'batchId';
    const mockCourses: Course[] = [];
    service.getCoursesByBatchId(batchId).subscribe((coursesResult) => {
      expect(coursesResult).toEqual(mockCourses);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/${batchId}/courses`);
    expect(req.request.method).toEqual('GET');
    req.flush(mockCourses);
  });


  it('should create a course', () => {
    const mockBatch: Batch = {
      batchId: 'batchId',
      batchName: 'batchName',
      batchDescription: 'batchDescription',
      editing: false
    };

    const mockCourse: Course = {
      courseId: '',
      batch: mockBatch,
      courseName: '',
      description: '',
      link: '',
      durationInHours: 0,
      estimatedStartDate: new Date(),
      estimatedEndDate: new Date(),
      subjectMatterExpert: '',
      editing: false
    };

    service.createCourse(mockCourse).subscribe((createdCourse) => {
      expect(createdCourse).toEqual(mockCourse);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/courses`);
    expect(req.request.method).toEqual('POST');
    req.flush(mockCourse);
  });


  afterEach(() => {
    httpTestingController.verify();
  });
});
