import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackBatchProgressComponent } from './track-batch-progress.component';
import { Router } from '@angular/router';
import { ReportService } from 'src/app/services/report.service';
import { BatchService } from 'src/app/services/batch.service';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatIcon } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';

describe('TrackBatchProgressComponent', () => {
  let component: TrackBatchProgressComponent;
  let fixture: ComponentFixture<TrackBatchProgressComponent>;
  let router : Router;
  let reportService : ReportService;
  let batchService : BatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrackBatchProgressComponent,SideNavBarComponent,MatIcon],
      imports: [FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [ReportService, BatchService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(TrackBatchProgressComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    reportService = TestBed.inject(ReportService);
    batchService = TestBed.inject(BatchService);
    fixture.detectChanges();
  });

  it('should create Track Batch Progress Component', () => {
    expect(component).toBeTruthy();
  });
});
