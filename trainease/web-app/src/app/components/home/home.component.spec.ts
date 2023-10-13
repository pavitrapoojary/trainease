import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { Router } from '@angular/router';
import { CourseService } from 'src/app/services/course.service';
import { BatchService } from 'src/app/services/batch.service';
import { ReportService } from 'src/app/services/report.service';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';


describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let router: Router;
  let courseService: CourseService;
  let batchService: BatchService;
  let reportService: ReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComponent, SideNavBarComponent],
      imports: [RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [BatchService, CourseService, ReportService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    courseService = TestBed.inject(CourseService);
    batchService = TestBed.inject(BatchService);
    reportService = TestBed.inject(ReportService);
    fixture.detectChanges();
  });

  it('should create Home Component', () => {
    expect(component).toBeTruthy();
  });

});
