import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { CreateBatchComponent } from './create-batch.component';
import { BatchService } from 'src/app/services/batch.service';
import { Router } from '@angular/router';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('CreateBatchComponent', () => {
  let component: CreateBatchComponent;
  let batchService: BatchService;
  let router: Router;
  let fixture: ComponentFixture<CreateBatchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateBatchComponent, SideNavBarComponent],
      imports: [FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [BatchService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateBatchComponent);
    component = fixture.componentInstance;
    batchService = TestBed.inject(BatchService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create Create Batch Component', () => {
    expect(component).toBeTruthy();
  });


  it('should initialize component properties', () => {
    expect(component.isBatchCreated).toBe(false);
    expect(component.isBatchCreateError).toBe(false);
    expect(component.isBatchEmpty).toBe(false);
    expect(component.originalErrorMessage).toBe('Batch creation error : ');
    expect(component.errorMessage).toBe('Batch creation error : ');
    expect(component.batch.batchId).toBe('');
    expect(component.batch.batchName).toBe('');
    expect(component.batch.batchDescription).toBe('');
    expect(component.batch.editing).toBe(false);
  });


  it('should show a warning message for empty batch details', fakeAsync(() => {
    component.batch.batchId = '';
    component.batch.batchName = '';
    component.batch.batchDescription = '';
    component.onSubmit();
    expect(component.isBatchEmpty).toBe(true);
    tick(2000);
    expect(component.isBatchEmpty).toBe(false);
  }));


});
