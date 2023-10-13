import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserComponent } from './add-user.component';
import { UserService } from 'src/app/services/user.service';
import { BatchService } from 'src/app/services/batch.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { UserRole } from 'src/app/models/user.model';
import { Batch } from 'src/app/models/batch.model';
import { of } from 'rxjs';


describe('AddUserComponent', () => {
  let component: AddUserComponent;
  let fixture: ComponentFixture<AddUserComponent>;
  let userService: UserService;
  let batchService: BatchService;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddUserComponent,SideNavBarComponent],
      imports: [FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [UserService, BatchService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(AddUserComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    batchService = TestBed.inject(BatchService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create Add user component', () => {
    expect(component).toBeTruthy();
  });


  it('should initialize component properties', () => {
    expect(component.displayForm).toBe(false);
    expect(component.displayExcel).toBe(false);
    expect(component.selectedBatch).toBe('');
    expect(component.user.emailId).toBe('');
    expect(component.user.name).toBe('');
    expect(component.user.role).toBe(UserRole.ADMIN);
    expect(component.user.batch.batchId).toBe('');
    expect(component.user.batch.batchName).toBe('');
    expect(component.user.batch.batchDescription).toBe('');
    expect(component.user.batch.editing).toBe(false);
    expect(component.user.editing).toBe(false);
    expect(component.userRoles).toEqual(Object.values(UserRole));
    expect(component.isUserAddedByExcel).toBe(false);
    expect(component.isErrorOccurredBySavingExcel).toBe(false);
    expect(component.errorMessage).toBe('User creation error : ');
    expect(component.errorMessageForm).toBe('User creation error : ');
    expect(component.isUserAddedByForm).toBe(false);
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


  it('should display the add user form', () => {
    component.displayAddUserForm();
    expect(component.displayForm).toBe(true);
    expect(component.displayExcel).toBe(false);
  });
  

  it('should display the Excel upload option', () => {
    component.displayExcelOption();
    expect(component.displayForm).toBe(false);
    expect(component.displayExcel).toBe(true);
  });

  
});
