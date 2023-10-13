import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCourseComponent } from './create-course.component';
import { Router } from '@angular/router';
import { CourseService } from 'src/app/services/course.service';
import { BatchService } from 'src/app/services/batch.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { Batch } from 'src/app/models/batch.model';
import { of } from 'rxjs';

describe('CreateCourseComponent', () => {
  let component: CreateCourseComponent;
  let router: Router;
  let courseService: CourseService;
  let batchService: BatchService;
  let httpClient: HttpClient;
  let fixture: ComponentFixture<CreateCourseComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateCourseComponent, SideNavBarComponent],
      imports: [FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [CourseService, BatchService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateCourseComponent);
    component = fixture.componentInstance;
    courseService = TestBed.inject(CourseService);
    batchService = TestBed.inject(BatchService);
    router = TestBed.inject(Router);
    httpClient = TestBed.inject(HttpClient);
    fixture.detectChanges();
  });

  it('should create Create Course Component', () => {
    expect(component).toBeTruthy();
  });


  it('should initialize component properties', () => {
    expect(component.displayForm).toBe(false);
    expect(component.displayExcel).toBe(false);
    expect(component.selectedBatch).toBe('');
    expect(component.course.courseId).toBe('');
    expect(component.course.durationInHours).toBe(0);
    expect(component.course.editing).toBe(false);
    expect(component.course.batch.editing).toBe(false);
    expect(component.isCourseAddedByExcel).toBe(false);
    expect(component.isErrorOccurredBySavingExcel).toBe(false);
    expect(component.errorMessage).toBe('Course creation error : ');
    expect(component.errorMessageForm).toBe('Course creation error : ');
    expect(component.isCourseAddedByForm).toEqual(false);
    expect(component.isErrorOccurredByForm).toBe(false);
  });


  it('should load batches on ngOnInit', () => {
    const batches: Batch[] = [
      { batchId: '1', batchName: 'Batch 1', batchDescription: '', editing: false },
      { batchId: '2', batchName: 'Batch 2', batchDescription: '', editing: false },
    ];

    spyOn(batchService, 'getAllBatches').and.returnValue(of(batches));

    component.ngOnInit();

    expect(component.batches).toEqual(batches);
  });


  it('should display the add course form', () => {
    component.displayAddCourseForm();
    expect(component.displayForm).toBe(true);
    expect(component.displayExcel).toBe(false);
  });


  it('should display the Excel upload option', () => {
    component.displayExcelOption();
    expect(component.displayForm).toBe(false);
    expect(component.displayExcel).toBe(true);
  });


});
