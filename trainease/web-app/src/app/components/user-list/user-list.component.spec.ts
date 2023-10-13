import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserListComponent } from './user-list.component';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { BatchService } from 'src/app/services/batch.service';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatIcon } from '@angular/material/icon';

describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let router : Router;
  let userService : UserService;
  let batchService : BatchService;


  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserListComponent,SideNavBarComponent,MatIcon],
      imports: [FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [UserService, BatchService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(UserListComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    userService = TestBed.inject(UserService);
    batchService = TestBed.inject(BatchService);
    fixture.detectChanges();
  });

  it('should create User List Component', () => {
    expect(component).toBeTruthy();
  });
});
