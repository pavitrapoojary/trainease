import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BatchListComponent } from './batch-list.component';
import { BatchService } from 'src/app/services/batch.service';
import { Router } from '@angular/router';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Batch } from 'src/app/models/batch.model';
import { of } from 'rxjs';

describe('BatchListComponent', () => {
  let component: BatchListComponent;
  let batchService : BatchService;
  let router : Router;
  let fixture: ComponentFixture<BatchListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BatchListComponent,SideNavBarComponent],
      imports:[FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers:[BatchService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(BatchListComponent);
    component = fixture.componentInstance;
    batchService = TestBed.inject(BatchService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create Batch List Component', () => {
    expect(component).toBeTruthy();
  });


  it('should initialize component properties', () => {
    expect(component.totalBatchesCount).toBe(0);
    expect(component.originalBatch.batchId).toBe('');
    expect(component.originalBatch.batchName).toBe('');
    expect(component.originalBatch.batchDescription).toBe('');
    expect(component.originalBatch.editing).toBe(false);
  });


  it('should load batches on ngOnInit and set total batch count', () => {
    const batches: Batch[] = [
      { batchId: '1', batchName: 'Batch 1', batchDescription: '', editing: false },
      { batchId: '2', batchName: 'Batch 2', batchDescription: '', editing: false },
    ];

    spyOn(batchService, 'getAllBatches').and.returnValue(of(batches));

    component.ngOnInit();

    expect(component.batches).toEqual(batches);
    expect(component.totalBatchesCount).toEqual(batches.length);
  });

  
});
