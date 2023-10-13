import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateProgressComponent } from './update-progress.component';
import { Router } from '@angular/router';
import { ProgressService } from 'src/app/services/progress.service';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('UpdateProgressComponent', () => {
  let component: UpdateProgressComponent;
  let fixture: ComponentFixture<UpdateProgressComponent>;
  let router : Router;
  let progressService : ProgressService;


  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateProgressComponent,SideNavBarComponent],
      imports: [FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [ProgressService, Router, HttpClient]
    }).compileComponents();
    
    fixture = TestBed.createComponent(UpdateProgressComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    progressService = TestBed.inject(ProgressService);
    fixture.detectChanges();
  });

  it('should create Update Progress Component', () => {
    expect(component).toBeTruthy();
  });
});
