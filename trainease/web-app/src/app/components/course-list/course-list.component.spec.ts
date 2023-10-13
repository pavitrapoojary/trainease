import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseListComponent } from './course-list.component';
import { CourseService } from 'src/app/services/course.service';
import { BatchService } from 'src/app/services/batch.service';
import { Router } from '@angular/router';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Course } from 'src/app/models/course.model';
import { of } from 'rxjs';

describe('CourseListComponent', () => {
  let component: CourseListComponent;
  let courseService:CourseService;
  let batchService : BatchService;
  let router : Router;

  let fixture: ComponentFixture<CourseListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CourseListComponent,SideNavBarComponent],
      imports: [RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [BatchService, Router, HttpClient]
    }).compileComponents();
    
    fixture = TestBed.createComponent(CourseListComponent);
    component = fixture.componentInstance;
    courseService = TestBed.inject(CourseService);
    batchService = TestBed.inject(BatchService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create Course List Component', () => {
    expect(component).toBeTruthy();
  });


  it('should initialize component properties', () => {
    expect(component.totalCoursesCount).toBe(0);
    expect(component.selectedBatch).toBe('');
    expect(component.selectedBatch).toBe('');
    expect(component.originalCourse.courseId).toBe('');
    expect(component.originalCourse.courseName).toBe('');
    expect(component.originalCourse.durationInHours).toBe(0);
    expect(component.originalCourse.editing).toBe(false);
  });


  it('should load courses on getCoursesByBatchId', () => {
    const courses: Course[] = [];
    const batchId = 'batchId';

    spyOn(courseService, 'getCoursesByBatchId').and.returnValue(of(courses));

    component.getCoursesByBatchId(batchId);

    expect(component.courses).toEqual(courses);
  });


});
