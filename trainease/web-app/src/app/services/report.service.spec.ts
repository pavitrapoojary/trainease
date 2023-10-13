import { TestBed } from '@angular/core/testing';

import { ReportService } from './report.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from 'src/environments/environment';

describe('ReportService', () => {
  let service: ReportService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReportService]
    });

    service = TestBed.inject(ReportService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created Report Service', () => {
    expect(service).toBeTruthy();
  });

  it('should get total courses in batch', () => {
    const batchId = 'batchId';
    service.getTotalCoursesInBatch(batchId).subscribe((totalCourses) => {
      expect(totalCourses).toEqual(2);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/report/totalCourses/${batchId}`);
    expect(req.request.method).toEqual('GET');
    req.flush(2);
  });

  it('should get total trainees in batch', () => {
    const batchId = 'batchId';
    service.getTotalTraineesInBatch(batchId).subscribe((totalCourses) => {
      expect(totalCourses).toEqual(2);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/report/totalTrainees/${batchId}`);
    expect(req.request.method).toEqual('GET');
    req.flush(2);
  });

  it('should get feedback list by course id', () => {
    const courseId = 'courseId';
    const feedbackList = ["feedback1", "feedback2"];
    service.getFeedbackListByCourseId(courseId).subscribe((totalCourses) => {
      expect(totalCourses).toEqual(feedbackList);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/report/feedback/${courseId}`);
    expect(req.request.method).toEqual('GET');
    req.flush(feedbackList);
  });


  afterEach(() => {
    httpTestingController.verify();
  });

});
