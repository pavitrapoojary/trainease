import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackProgressComponent } from './track-progress.component';
import { Router } from '@angular/router';
import { ProgressService } from 'src/app/services/progress.service';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { AgGridAngular } from 'ag-grid-angular';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatIcon } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';

describe('TrackProgressComponent', () => {
  let component: TrackProgressComponent;
  let fixture: ComponentFixture<TrackProgressComponent>;
  let router : Router;
  let courseProgressService : ProgressService;


  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrackProgressComponent,SideNavBarComponent,AgGridAngular,MatIcon],
      imports: [ FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [ProgressService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(TrackProgressComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    courseProgressService = TestBed.inject(ProgressService);
    fixture.detectChanges();
  });

  it('should create Track Progress Component', () => {
    expect(component).toBeTruthy();
  });
});
